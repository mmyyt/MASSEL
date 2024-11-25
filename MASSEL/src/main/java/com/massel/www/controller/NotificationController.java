package com.massel.www.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.management.Notification;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.massel.www.domain.ChatVO;
import com.massel.www.domain.NotificationVO;
import com.massel.www.domain.OrderStatus;
import com.massel.www.domain.UserImgDTO;
import com.massel.www.domain.UserVO;
import com.massel.www.repository.SaleOrdererDAO;
import com.massel.www.service.ChatService;
import com.massel.www.service.NotificationService;
import com.massel.www.service.SaleServiceImpl1;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/notification")
@Slf4j
@Controller
public class NotificationController {
	
	private final SimpMessagingTemplate messagingTemplate; //클라이언트로 메세지를 전송할때 사용
	
	@Autowired
	public NotificationController (SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }
	
	@Inject
	private NotificationService nsv;
	
	@Autowired(required = false)
	private SaleServiceImpl1 ssv;
	
	@Inject
	private ChatService csv;

	@MessageMapping("/notification/user/{writer}")
	@SendTo("/queue/user/{writer}")
	public Map<String, String> notifyPaymentCompleted(@PathVariable String writer,
			@Payload Map<String, String> payload ) {
		log.info("판매자에게 입금 완료사실 알람 보내기~@@@@@@@@@@@");
		//판매자에게 입금이 완료되었다는 알람 보내기
		
		String writerId = (String)payload.get("writer");
		String sessionId = (String)payload.get("sessionId");
		//int orderNo = Integer.parseInt(payload.get("orderNo"));
		String orderNo = (String)payload.get("orderNo");
		log.info("판매자ID >>"+writerId+", 구매자ID >>"+sessionId);
		
		//메세지 보내기
		//구매자ID님이 입금을 완료하셨습니다.
		//입금을 확인 해주세요.
		//알람보낸시각
		LocalDateTime now = LocalDateTime.now();
		String formattedDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		
		//String message = "주문번호 : "+orderNo+"  "+sessionId+"님이 입금을 완료하셨습니다. 입금을 확인해주세요.";
		Map<String, String> message = new HashMap<>();
		message.put("writerId", writerId);
		message.put("sessionId", sessionId);
		message.put("orderNo", orderNo);
		message.put("date",formattedDate);
		
		log.info("message 값 >>>> "+message.toString());

		//messagingTemplate.convertAndSend("/queue/user/"+writer, message);
		
		//해당 메세지 값 db에 넣기
		String messageContent = sessionId+"님이 주문번호 "+orderNo+" 에 입금을 완료했습니다. 입금 내역을 확인해주세요.";
		NotificationVO nvo = new NotificationVO();
		nvo.setListener(writerId);
		nvo.setMessageContent(messageContent);
		log.info(messageContent);
		
		int isOk = nsv.insertMessage(nvo);
		log.info((isOk>0)?"메세지insert성공":"메세지insert실패");
		
		//입금완료상태로 변경하기
		int realOrderNo = Integer.parseInt(payload.get("orderNo"));
		String orderStatus = OrderStatus.PAYMENT_COMPLETED.name();
		isOk = ssv.updateOrderStatus(realOrderNo, orderStatus);
		log.info((isOk>0)?"상태update성공":"상태update실패");
		
		
		return message;
		
	}
	
	@GetMapping(value = "/checkIsRead", produces = "application/json")
	@ResponseBody
	public int checkIsReadCount(HttpServletRequest srq){
		//세션에 로그인되어있는 회원의 알람함 is_read 카운트
		HttpSession session = srq.getSession();
		UserImgDTO user = (UserImgDTO)session.getAttribute("ses");
		String userId = user.getUvo().getId();
		
		//notification에 해당 회원의 데이터가 is_read = 0 인 것 가져오기 (안읽음)
		int count = nsv.getIsReadCount(userId);
		if(count>0) {
			//안읽은 메세지가 있다는 의미
			log.info("안읽은 메세지 존재!");
		}else {
			log.info("메세지 다 읽음");
		}
		
		return count;
	}
	@ResponseBody
	@PostMapping(value = "/updateIsRead", produces = "application/json")
	public int updateIsRead(HttpServletRequest srq){
		log.info("isRead 0인거 1로 업데이투");
		HttpSession session = srq.getSession();
		UserImgDTO user = (UserImgDTO)session.getAttribute("ses");
		String userId = user.getUvo().getId();
		
		int isOk = nsv.updateIsRead(userId);
		
		log.info((isOk>0)? "is Read 업데이트 성공":"is Read 업데이트 없음");
		
		return isOk;
	}
	
	@GetMapping("/alarm")
	public String userAlarm(Model m, HttpServletRequest srq) {
		log.info("유저 알람 페이지");
		HttpSession session = srq.getSession();
		UserImgDTO user = (UserImgDTO)session.getAttribute("ses");
		String userId = user.getUvo().getId();
		
		List<NotificationVO> list = nsv.getList(userId);
		m.addAttribute("list", list);
		
		return "/user/alarm";
	}
	
	
	//새로운 채팅 알람
	@MessageMapping("/notification/chat/{roomId}/{recipient}")
	@SendTo("/queue/user/chat/{recipient}")
	public Map<String, String> notifyNewChat(@DestinationVariable String roomId, @DestinationVariable String recipient, ChatVO message){
		String sender  = message.getSender();
		Map<String, String> notice = new HashMap<>();
		
		String m = sender+"님이 메세지를 보냈습니다.";
		
		notice.put("m", m);
		
		return notice;
	}
	
	
	//채팅 읽었는지 확인
	@ResponseBody
	@GetMapping(value = "/chatIsRead", produces = "application/json")
	public int checkChatIsRead(HttpServletRequest srq) {
		HttpSession session = srq.getSession();
		UserImgDTO user = (UserImgDTO)session.getAttribute("ses");
		
		String userId = user.getUvo().getId();
		
		int count = csv.getChatIsRead(userId);
		
		return count;
	}
	
}
