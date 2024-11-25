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
public class SaleUserParticipationVO {
	
	//유저가 구매한 상품 리스트 출력을 위한 VO
	private String orderDate;  //주문날짜
	private int orderNo; //주문번호
	private SaleVO svo;
	private SaleImgVO sivo;
}
