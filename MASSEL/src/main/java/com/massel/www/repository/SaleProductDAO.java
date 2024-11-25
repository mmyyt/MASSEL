package com.massel.www.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.massel.www.domain.SaleProductVO;

public interface SaleProductDAO {

	int registerSaleProduct(SaleProductVO spvo);

	List<SaleProductVO> getProductList(int sno);

	SaleProductVO getProduct(int spno);

	int updateStock(@Param("spno") int spno,@Param("count") int count);

	int getLastSpno();

	int deleteProduct(int sno);

}
