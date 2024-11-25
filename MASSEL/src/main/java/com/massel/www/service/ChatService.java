package com.massel.www.service;

import java.util.List;

import com.massel.www.domain.ChatReadVO;
import com.massel.www.domain.ChatRoomVO;
import com.massel.www.domain.ChatVO;

public interface ChatService {

	//채팅방 있는지 없는지 확인해서 없음 만들고 있음 방번호 return
	String getOrcreateChatRoom(String sender, String targetUser);

	//채팅방 정보
	ChatRoomVO getChatRoom(String roomId);

	//채팅 넣기
	int insertChat(ChatVO message);

	//채팅내역가져오기
	List<ChatVO> getChatHistory(String roomId);

	//채팅방 리스트 가져오기
	List<ChatRoomVO> getChatRoomList(String userId);

	//마지막채팅내역 가져오기
	ChatVO getLastChat(String roomId);

	//isExit 업데이트
	int updateIsExit(String id, String roomId);

	//방삭제를 위한 count 얻기(is_exit=1 이 2개면)
	int getIsExitCount(String roomId);

	//room삭제
	int deleteRoom(String roomId);

	//chat Read insert
	int insertChatRead(ChatReadVO crvo);

	//마지막으로 isert 된 객체의 id가져옿기
	int getLastMessageId();

	//채팅방 isRead count
	int getChatIsRead(String userId);

	//채팅방 isRead count + roomId(리스트에 출력할거)
	int getIsRead(String roomId, String userId);

	//채팅방 isRead update
	int updateIsRead(String roomId, String id);

}
