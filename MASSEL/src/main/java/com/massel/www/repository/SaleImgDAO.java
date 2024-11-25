package com.massel.www.repository;

import org.apache.ibatis.annotations.Param;

import com.massel.www.domain.SaleImgVO;

public interface SaleImgDAO {

	int registerSaleImg(SaleImgVO sivo);

	SaleImgVO getImgList(int sno);

	int updateSaleImg(@Param("file")SaleImgVO file,@Param("sno") int sno);

}
