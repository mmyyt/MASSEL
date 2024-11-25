package com.massel.www.repository;

import java.util.List;

import com.massel.www.domain.SaleShipmentVO;

public interface SaleShipmentDAO {

	int registerSaleShipping(SaleShipmentVO ssvo);

	List<SaleShipmentVO> getShipmentMethod(int sno);

	int deleteShipment(int sno);

}
