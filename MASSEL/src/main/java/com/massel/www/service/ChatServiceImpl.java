package com.massel.www.service;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.massel.www.domain.ChatReadVO;
import com.massel.www.domain.ChatRoomVO;
import com.massel.www.domain.ChatVO;
import com.massel.www.repository.ChatDAO;
import com.massel.www.repository.ChatReadDAO;
import com.massel.www.repository.ChatRoomDAO;
import com.massel.www.repository.ChatRoomExitDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ChatServiceImpl implements ChatService {


	@Inject
	private ChatDAO cdao;
	
	@Inject
	private ChatRoomDAO crdao;
	
	@Inject
	private ChatRoomExitDAO credao;
	
	@Inject
	private ChatReadDAO cedao;
	
	@Override
	public String getOrcreateChatRoom(String sender, String targetUser) {
		log.info(sender+" 와 "+targetUser+" 의 방 생성");
		
		//기존의 두 사용자 간의 방이 있는지 확인
		ChatRoomVO existingRoom = crdao.getExistingRoom(sender, targetUser);
		

		if(existingRoom != null) {
			//방이 존재한다면 해당 방 id를 반환
			return existingRoom.getRoomId();
		}else {
			//방 없으면 새로 만들어
			String roomId = UUID.randomUUID().toString();
			log.info("방 ID >>>>>>>>>>>>"+roomId);
			//ChatRoomVO newRoom = crdao.createNewRoom(roomId, sender, targetUser);
			
			ChatRoomVO newRoom = new ChatRoomVO();
			newRoom.setRoomId(roomId);
			newRoom.setSender(sender);
			newRoom.setRecipient(targetUser);
			
			int isOk = crdao.createNewRoom(newRoom);
			
			//방을 새로 만들면서 is_exit에다가도 sender,targetUser 넣어줌 isExit = 0
			log.info("is Exit에다가 넣어주기!!");
			isOk = credao.insertRoomExit(sender, roomId, 0);
			isOk = credao.insertRoomExit(targetUser, roomId, 0);
			
			if(isOk>0) {
				log.info("성공");
			}else {
				log.info("실패");
			}
			
			log.info("새로운 방의 정보 >> "+newRoom);
			
			return roomId;
		}

	}

	//방번호로 해당 사용자 정보 가져오기
	@Override
	public ChatRoomVO getChatRoom(String roomId) {
		
		return crdao.getChatRoom(roomId);
	}

	//해당 방의 정보 집어넣기
	@Override
	public int insertChat(ChatVO message) {
		
		return cdao.insertChat(message);
	}

	//채팅 내역 가져오기
	@Override
	public List<ChatVO> getChatHistory(String roomId) {
		log.info("채팅 내역 가쟈오기~");
		return cdao.getChatHistory(roomId);
	}

	//채팅방 리스트 가져오기
	@Override
	public List<ChatRoomVO> getChatRoomList(String userId) {
		log.info("채팅방 리스트 가져오기~ ");
		return crdao.getChatRoomList(userId);
	}

	@Override
	public ChatVO getLastChat(String roomId) {
		log.info("마지막 채팅 기록 가꼬오기");
		return cdao.getLastChat(roomId);
	}

	//isExit update
	@Override
	public int updateIsExit(String id, String roomId) {
		log.info("isExit update");
		return credao.updateIsExit(id,roomId);
	}

	@Override
	public int getIsExitCount(String roomId) {
		// TODO Auto-generated method stub
		return credao.getIsExitCount(roomId);
	}

	@Override
	public int deleteRoom(String roomId) {
		log.info("room delete!");
		return crdao.deleteRoom(roomId);
	}

	@Override
	public int insertChatRead(ChatReadVO crvo) {
		// TODO Auto-generated method stub
		return cedao.insertChatRead(crvo);
	}

	@Override
	public int getLastMessageId() {
		// TODO Auto-generated method stub
		return cdao.getLastMessageId();
	}

	@Override
	public int getChatIsRead(String userId) {
		log.info("chat_isRead Check");
		return cedao.getIsReadCount(userId);
	}

	@Override
	public int getIsRead(String roomId, String userId) {
		// TODO Auto-generated method stub
		return cedao.getIsRead(roomId, userId);
	}

	@Override
	public int updateIsRead(String roomId, String id) {
		// TODO Auto-generated method stub
		return cedao.updateIsRead(roomId, id);
	}



}
