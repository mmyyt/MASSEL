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
public class SaleOrdererParticipationDTO {

	private List<SaleOrdererProductVO> userProductList;
	private SaleOrdererVO saleOrdererData;
	private SaleOrdererShipment saleOrdererShippingData; //javascript에서 전송된 키 값이랑 일치해야함..!!! 
}
