package com.massel.www.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SaleDTO {

	private SaleVO svo;  //글
	private SaleImgVO sivo;  //이미지
	private SaleSellerVO slvo;  //계좌정보
	private List<SaleProductDTO> spdto;  //제품
	private List<SaleShipmentVO> ssvo;  //배송
}
