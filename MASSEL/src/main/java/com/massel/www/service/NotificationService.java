package com.massel.www.service;

import java.util.List;

import com.massel.www.domain.NotificationVO;

public interface NotificationService {

	int insertMessage(NotificationVO nvo);

	int getIsReadCount(String userId);

	int updateIsRead(String userId);

	List<NotificationVO> getList(String userId);

}
