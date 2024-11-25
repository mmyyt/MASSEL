package com.massel.www.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatVO {

	private int messageId; //메세지 고유ID
    private String roomId;// 방 번호
    private String sender;// 메세지를 보낸 사람
    private String recipient;
    private String content;// 메세지 내용
    private String sendDate; // 메세지 발송 시간
    private boolean isRead;
}
