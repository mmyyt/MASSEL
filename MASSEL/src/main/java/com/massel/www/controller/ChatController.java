package com.massel.www.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.massel.www.domain.ChatDTO;
import com.massel.www.domain.ChatReadVO;
import com.massel.www.domain.ChatRoomVO;
import com.massel.www.domain.ChatVO;
import com.massel.www.domain.UserImgDTO;
import com.massel.www.domain.UserImgVO;
import com.massel.www.domain.UserVO;
import com.massel.www.service.ChatService;
import com.massel.www.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/chat")
@Controller
public class ChatController {
	
	@Inject
	private ChatService csv;
	
	@Inject
	private UserService usv;
	
	private final SimpMessagingTemplate messagingTemplate; //클라이언트로 메세지를 전송할때 사용
	
	@Autowired
	public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }
	
	//채팅방으로 이동(채팅 목록)
	@GetMapping("/chatRoom")
	public String goChatRoom(HttpServletRequest srq, Model m) {
		//채팅방 목록을 뿌려주기
		//내가 현재 참여해있는 채팅방 가져오기
		HttpSession session = srq.getSession();
		UserImgDTO user = (UserImgDTO)session.getAttribute("ses");
		
		String userId = user.getUvo().getId();
		
		//내가 sender든 recipient든 참여하고 있는 채팅방 리스트를 가져오기 (is_exit가 0인것만)
		List<ChatRoomVO> list = csv.getChatRoomList(userId);
		
		m.addAttribute("list", list);

		List<ChatDTO> cdto = new ArrayList<ChatDTO>();
		
		//채팅방, 마지막 메세지를 함께 저장할 리스트
		for(ChatRoomVO crvo : list) {
			String roomId = crvo.getRoomId();
			ChatVO cvo = csv.getLastChat(roomId); //해당roomId를 주고 마지막채팅을 기록
			//해방 채팅방의 is_read count 세기
			int readCount = csv.getIsRead(roomId, userId);
			
			cdto.add(new ChatDTO(crvo, cvo, readCount));

		}
		
		m.addAttribute("cdto", cdto);
		
		//상대방의 프로필 사진 가져오기
		
		for(ChatRoomVO crvo : list) {
			
			String sender = crvo.getSender();
			log.info("sender >> "+sender);
			String recipient = crvo.getRecipient();
			log.info("recipient >> "+recipient);
			
			log.info("내 아이디 >> "+userId);
			//만약 userId가 sender랑 같으면 recipient의 아이디로 이미지 가져오기
			if(userId.equals(sender)) {
				log.info(recipient+"의 이미지 가져오기");
				UserImgVO uivo = usv.getUserImg(recipient);
				m.addAttribute("uivo",uivo);
			}else if(userId.equals(recipient)) {
				//userId가 recipient랑 같으면 sender의 아이디로 이미지 가져오기
				log.info(sender+"의 이미지 가져오기");
				UserImgVO uivo = usv.getUserImg(sender);
				m.addAttribute("uivo",uivo);
			}
			
		}

		return "/chat/chatRoomList";
	}
		
	//방 생성 요청
	@PostMapping("/createRoom")
	@ResponseBody
	public Map<String, String> createChatRoom(@RequestParam String targetUser, HttpServletRequest srq){
		//sender랑 targetUser간의 방 생성
		//sender -> 현재 session에 로그인되어있는 유저
		HttpSession session = srq.getSession();
		UserImgDTO user = (UserImgDTO)session.getAttribute("ses");
		String sender = user.getUvo().getId();
		log.info("메세지 전송자 >>> "+sender+" || 메세지 수신자 >>> "+targetUser);
		
		//둘 사이에 방이 존재하는지 확인하고 있으면 roomId반환, 없으면 새로 만들고 roomId반환
		String roomId = csv.getOrcreateChatRoom(sender, targetUser);
		log.info("반환된 roomId >>>> "+roomId);
		//방 ID 클라이언트로 반환
		Map<String, String> response = new HashMap<>();
		response.put("roomId", roomId);
		
		return response;
		
	}
	
	@GetMapping("/room/{roomId}")
	public String enterChatRoom(@PathVariable String roomId, Model m, HttpServletRequest srq) {
		log.info("방 번호 >>> "+roomId);
		m.addAttribute("roomId", roomId);
		//sender를 알 수 있는 방법?
		HttpSession session = srq.getSession();
		UserImgDTO sender = (UserImgDTO)session.getAttribute("ses");
		
		if(sender!=null) {
			m.addAttribute("sender", sender.getUvo().getId());
		}
		
		//방의 정보도 같이 가져와서 상대방 정보도 같이 전달하기
		ChatRoomVO chatRoom = csv.getChatRoom(roomId);
		log.info("해당 채팅방 정보 >>>> "+chatRoom);
		
		//채팅 내역 가져오기
		List<ChatVO> chatHistory = csv.getChatHistory(roomId);
		m.addAttribute("chatList", chatHistory);
		
		if(chatRoom != null) {  //sender / recipient 구분하기
			if(sender!=null && sender.getUvo().getId().equals(chatRoom.getSender())) { //내가 방만든사람(먼저보낸사람)
				m.addAttribute("recipient", chatRoom.getRecipient()); //그럼 상대방이 recipient
			}else { //내가 메세지 받은사람
				m.addAttribute("recipient", chatRoom.getSender());//그럼 상대방이 sender
			}
		}
		
		//is_read가 0인걸 1로 update하기
		int isOk = csv.updateIsRead(roomId, sender.getUvo().getId());
		
		log.info((isOk>0)? "isRead 1 업데이트 성공":"isRead 1 업데이트 실패");
		
		//해당방의 exit가 1 인게 있는지 확인
		int isExitCount = csv.getIsExitCount(roomId);
		m.addAttribute("exitCount", isExitCount);
		
		//상대방의 이미지 사진 가져오기
		String chatRecipient = chatRoom.getRecipient();
		String chatSender = chatRoom.getSender();
		
		String userId = sender.getUvo().getId();		
		//userId == 세션에 로그인되어있는 나
		if(userId.equals(chatSender)) {
			//recipient 이미지 가져오기
			UserImgVO uivo = usv.getUserImg(chatRecipient);
			m.addAttribute("uivo",uivo);
		}else if(userId.equals(chatRecipient)) {
			//sender 이미지 가져오기
			UserImgVO uivo = usv.getUserImg(chatSender);
			m.addAttribute("uivo",uivo);
		}
		
		return "/chat/room";
	}
	
	
	@MessageMapping("/chat.sendMessage/{roomId}")
	@SendTo("/topic/room/{roomId}")
	public ChatVO sendMessage(@DestinationVariable String roomId, ChatVO message) {
		log.info("메세지 >>> "+message);
		//roomId 설정
		message.setRoomId(roomId);
		//시간설정하기
		message.setSendDate(LocalDateTime.now().toString());
		//db에 등록하기
		
		int isOk = csv.insertChat(message);
		
		if(isOk > 0 ) {
			log.info("메세지 등록 성공");
			
			int messageId = csv.getLastMessageId();
			ChatReadVO crvo = new ChatReadVO();
			crvo.setMessageId(messageId);
			crvo.setUserId(message.getRecipient());
			crvo.setRoomId(roomId);
			
			int status = csv.insertChatRead(crvo);
			log.info((status>0)?"read insert 성공":"read insert 실패");
			
		}else {
			log.info("메세지 저장 실패");
		}

		
		return message;
	}
	
	//나가기 버튼-> is_exit update
	@PostMapping(value = "/exit", produces = "application/json")
	public ResponseEntity<?> updateExit(HttpServletRequest srq,@RequestParam String id, @RequestParam String roomId ) {
		log.info("나가기버튼 update!");
		log.info("id >> "+id+", roomId >> "+roomId);  
		
		int isOk = csv.updateIsExit(id, roomId);
		//해당 roomId의 is_exit = 1 인거 count세고 2이상이면 해당 room삭제
		int isExitCount = csv.getIsExitCount(roomId); 
		if(isExitCount == 2 ) {
			isOk = csv.deleteRoom(roomId);
		}

		HttpSession session = srq.getSession();
		UserImgDTO user = (UserImgDTO)session.getAttribute("ses");
		String currentUserId = user.getUvo().getId();
		
		Map<String, String> response = new HashMap<String, String>();
		if(isOk > 0) {
			log.info("isExit = 1로 update 성공");
			response.put("status", "success");
			
			//업데이트 성공 -> stomp 메세지 전송(해당 구독 경로로 메세지 전송)
			Map<String, String> exitMessage = new HashMap<String, String>();
			exitMessage.put("action", "exit");
			messagingTemplate.convertAndSend("/topic/room/"+roomId, exitMessage);
			
			//session에 있는 id == 버튼 누른 id 같으면 self
			if(id.equals(currentUserId)) {
				response.put("user", "self");
			}
			
		}else {
			log.info("isExit = 1로 update 실패");
			response.put("status", "failure");
		}
		return ResponseEntity.ok(response);
	}
	
	//해당 roomId에 유저가 접속했음을 확인
	private Map<String, String> userRoomMap = new HashMap<String, String>();
	@MessageMapping("/chat.joinRoom")
	public void joinRoom(@Payload Map<String, String> payload) {
		String roomId = payload.get("roomId");
		String userId = payload.get("userId");
		
		userRoomMap.put(userId, roomId);
		log.info(userId+" 가 "+roomId+"에 입장");
	}
	
	//해당 roomId에 유저가 나갔음을 확인
	@MessageMapping("/chat.leaveRoom")
	public void leaveRoom(@Payload Map<String, String> payload) {
	    String roomId = payload.get("roomId");
	    String userId = payload.get("userId");
	    
	    // userRoomMap에서 해당 유저를 제거
	    if (userRoomMap.containsKey(userId)) {
	        userRoomMap.remove(userId);
	        log.info(userId + "가 " + roomId + " 방에서 나갔습니다.");
	    }
	}
}
