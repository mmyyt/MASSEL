package com.massel.www.repository;

import com.massel.www.domain.SaleOrdererProductVO;

public interface SaleOrdererProductDAO {

	int insertUserProduct(SaleOrdererProductVO sopvo);

	//최대주문번호 구하기
	int getMaxOrderNo();

	//참여자 수 구하기
	int getParticipationCount(int sno);

}
