package com.massel.www.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NotificationVO {

	private String listener;
	private int isRead;
	private String recievedDate;
	private String messageContent;
	
}
