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
public class SaleShipmentVO {
	//판매글의 배송정보
	
	private int sno;
	private String shippingMethod;
	private int shippingCost;
}
