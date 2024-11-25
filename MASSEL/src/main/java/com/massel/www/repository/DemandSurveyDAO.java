package com.massel.www.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.massel.www.domain.DemandSurveyVO;
import com.massel.www.domain.PagingVO;

public interface DemandSurveyDAO {

	int insertSurvey(DemandSurveyVO dsvo);

	List<DemandSurveyVO> getDSList(PagingVO pvo);

	DemandSurveyVO getDSDetail(int dno);

	int updateDS(DemandSurveyVO dsvo);

	int selectDno();

	int tmpRegister(DemandSurveyVO dsvo);

	int registerDS(DemandSurveyVO dsvo);

	//글+썸네일 등록
	int insertDemandSurvey(DemandSurveyVO dsvo);

	List<DemandSurveyVO> getDsList(String userID);

	//cron식을 위해 글가져오기
	List<DemandSurveyVO> getDsFormList(String endStatus);

	//status변경
	int updateStatus(@Param("dno")int dno,@Param("status") String status);
	
	//조회수증가
	int updateReadCount(int dno);
	
	//메인에 가져올 최신순 10개
	List<DemandSurveyVO> getDemandSurvey();

	int updateDemandSurvey(DemandSurveyVO dsvo);

	int deleteDemand(int dno);

	List<DemandSurveyVO> getDemandSurveySearch(String keyword);

	List<DemandSurveyVO> getPopularList();

	int getTotalCount(PagingVO pvo);

	int getLastInsertedDno();




}
