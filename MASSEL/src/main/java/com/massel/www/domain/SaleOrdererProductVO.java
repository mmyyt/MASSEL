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
public class SaleOrdererProductVO {
	
	private int sno;  //글번호
	private int orderNo; //주문번호
	private String id; //주문자 아이디
	private int spno;  //상품번호
	private String spname; //상품이름
	private int spprice;  //상품가격
	private int count;  //개수
	private String orderDate; //주문날짜
	private int totalPrice; //총가격

}
