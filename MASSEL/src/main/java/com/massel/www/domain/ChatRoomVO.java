package com.massel.www.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomVO {

	private String roomId; //채팅방ID
	private String sender; //처음 채팅을 시작한 사람
	private String recipient; //채팅을 받은 사람
	private String createDate; //방 생성 날짜
}
