package com.massel.www.repository;

import org.apache.ibatis.annotations.Param;

import com.massel.www.domain.SaleSellerVO;

public interface SaleSellerDAO {

	SaleSellerVO getSellerAccount(int sno);

	int insertSellerAccount(SaleSellerVO sellerInfo);

	int updateSellerInfo(@Param("bankName")String bankName,@Param("accountNumber") String accountNumber,
			@Param("accountHolder")String accountHolder,@Param("sno") int sno);

	int deleteSellerInfo(int sno);

}
