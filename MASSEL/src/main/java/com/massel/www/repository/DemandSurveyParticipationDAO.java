package com.massel.www.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.massel.www.domain.DemandSurveyParticipationVO;

public interface DemandSurveyParticipationDAO {

	int insertDemandParticipation(DemandSurveyParticipationVO dsppvo);

	int getParticipantCount(int dno);

	List<DemandSurveyParticipationVO> getParticipation(int dno);

	List<DemandSurveyParticipationVO> getMyParticipation(String id);

	List<DemandSurveyParticipationVO> getUserDemandFormDeatil(@Param("userId")String userId, @Param("dno")int dno);

	DemandSurveyParticipationVO getParticipationInfo(@Param("userId") String userId, @Param("dno") int dno);

	int isParticipated(@Param("userID")String userID,@Param("dno")int dno);

}
