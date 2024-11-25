package com.massel.www.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.massel.www.domain.NotificationVO;
import com.massel.www.repository.NotificationDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NotificationServiceImpl implements NotificationService {

	@Inject
	private NotificationDAO ndao;
	
	@Override
	public int insertMessage(NotificationVO nvo) {
		log.info("messageContent 저장 service");
		return ndao.insertMessage(nvo);
	}
	
	//isRead = 0 인것 count
	@Override
	public int getIsReadCount(String userId) {
		log.info("message isRead count");
		return ndao.getIsReadCount(userId);
	}

	//isRead = 0인것 1로 update
	@Override
	public int updateIsRead(String userId) {
		log.info("message isRead update");
		return ndao.updateIsRead(userId);
	}

	//유저의 알람 목록 가져요기
	@Override
	public List<NotificationVO> getList(String userId) {
		log.info("message List get");
		return ndao.getMessageList(userId);
	}

}
