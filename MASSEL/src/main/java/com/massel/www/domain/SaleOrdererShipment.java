package com.massel.www.domain;

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
public class SaleOrdererShipment {
	
	private int sno;   //글번호
	private int orderNo;  //주문번호
	private String id;  //주문자 아이디
	private String recipientName;  //수령인이름
	private String recipientPhoneNumber; //수령인연락처
	private String recipientEmail; //수령인이메일
	private String recipientPostalCode; //우편번호
	private String recipientAddress;
	private String recipientDetailAddress; //상세주소
	private String shippingMethod; //배송방법
	private int shippingCost;  //배송비
	private String shippingNote; //배송메모
}
