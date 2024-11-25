package com.massel.www.repository;

import java.util.List;

import com.massel.www.domain.NotificationVO;

public interface NotificationDAO {

	int insertMessage(NotificationVO nvo);

	int getIsReadCount(String userId);

	int updateIsRead(String userId);

	List<NotificationVO> getMessageList(String userId);

}
