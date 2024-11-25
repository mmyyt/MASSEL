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
public class SaleOrdererVO {
	
	private int sno;  //글번호
	private int orderNo;  //주문번호
	private String id;   //아이디
	private String nickname;  //닉네임
	private String name;  //주문자 이름
	private String email; //이메일
	private String phoneNumber; //주문자 연락처
	private String snsId; //주문자 sns아이디
	private String refundAccount; //주문자 환불계좌
	private String refundBank; //주문자 환불계좌은행
	private String refundAccountHolder;  //예금주
	private String orderDate; //주문날짜
	private OrderStatus orderStatus; //주문상태

}