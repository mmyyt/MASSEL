package com.massel.www.repository;

import java.util.List;

import com.massel.www.domain.DemandSurveyProductVO;

public interface DemandSurveyProductDAO {

	int registerProductList(DemandSurveyProductVO dsvo);

	List<DemandSurveyProductVO> getProductList(int dno);

	DemandSurveyProductVO getProduct(int dpno);

	int deleteOriginal(int dno);


}
