package com.massel.www.repository;

import java.util.List;

import com.massel.www.domain.ChatVO;

public interface ChatDAO {

	int insertChat(ChatVO message);

	List<ChatVO> getChatHistory(String roomId);

	ChatVO getLastChat(String roomId);

	int getLastMessageId();




}
