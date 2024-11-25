package com.massel.www.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.massel.www.domain.ChatRoomVO;

public interface ChatRoomDAO {

	int createNewRoom(ChatRoomVO newRoom);

	ChatRoomVO getExistingRoom(@Param("sender")String sender, @Param("recipient")String targetUser);

	ChatRoomVO getChatRoom(String roomId);

	//채팅방 리스트 가져오기
	List<ChatRoomVO> getChatRoomList(String userId);

	//room삭제
	int deleteRoom(String roomId);
}
