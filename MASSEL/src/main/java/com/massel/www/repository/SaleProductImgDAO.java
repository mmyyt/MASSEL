package com.massel.www.repository;

import com.massel.www.domain.SaleProductImgVO;

public interface SaleProductImgDAO {

	int registerProductImg(SaleProductImgVO file);

	SaleProductImgVO getProductImg(int spno);

}
