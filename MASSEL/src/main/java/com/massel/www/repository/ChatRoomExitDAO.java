package com.massel.www.repository;

import org.apache.ibatis.annotations.Param;

public interface ChatRoomExitDAO {

	int insertRoomExit(@Param("sender") String sender, @Param("roomId")String roomId, @Param("i") int i);

	int updateIsExit(@Param("id") String id,@Param("roomId") String roomId);

	int getIsExitCount(String roomId);

	int checkIsExit(String roomId);

}
