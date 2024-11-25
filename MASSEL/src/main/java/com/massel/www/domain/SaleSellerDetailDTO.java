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
public class SaleSellerDetailDTO {

	//해당 글의 detail정보를 위한 dto
	private SaleVO svo;
	private SaleImgVO sivo;
	private List<SaleProductDTO> spdtoList;
//	private SaleOrdererVO sovo;
//	private List<SaleOrdererProductVO> sopvo;
//	private SaleOrdererShipment sosvo;
}
