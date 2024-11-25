package com.massel.www.domain;

import java.time.LocalDateTime;

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
public class SaleVO {

	private int sno;
	private String swriter;
	private String stitle;
	private String sdetail;
	private int category;
	private String sregDate;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private int isDel;
	private boolean isEnd;
	private int viewCount;
	private int favoriteCount;
	private String refundMessage;
	private String fullDate;
	private String shippingExdate;  //배송예정날짜
	private String shippingInstructions; //배송관련안내사항
	private String status; //폼 진행상태

}
