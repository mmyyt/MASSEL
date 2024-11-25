package com.massel.www.repository;

import org.apache.ibatis.annotations.Param;

import com.massel.www.domain.ChatReadVO;

public interface ChatReadDAO {

	int insertChatRead(ChatReadVO crvo);

	int getIsReadCount(String userId);

	int getIsRead(@Param("roomId")String roomId, @Param("userId")String userId);

	int updateIsRead(@Param("roomId")String roomId,@Param("id")String id);

}
