package com.massel.www.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChatReadVO {
	
	private int messageId;
	private String userId; //메세지 수신자
	private int isRead;
	private String roomId;
}
