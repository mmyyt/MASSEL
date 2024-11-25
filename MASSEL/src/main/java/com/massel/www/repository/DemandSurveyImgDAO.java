package com.massel.www.repository;

import org.apache.ibatis.annotations.Param;

import com.massel.www.domain.DemandSurveyImgVO;

public interface DemandSurveyImgDAO {

	int registerDSImg(DemandSurveyImgVO dsivo);

	DemandSurveyImgVO getDSimgList(int dno);

	DemandSurveyImgVO getThumbnailFile(int dno);

	int updateDemandSurveyImg(@Param("dsivo")DemandSurveyImgVO dsivo, @Param("dno") int dno);


}
