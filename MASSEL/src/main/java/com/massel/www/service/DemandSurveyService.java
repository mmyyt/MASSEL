package com.massel.www.service;

import java.util.List;

import com.massel.www.domain.DemandSurveyDTO;
import com.massel.www.domain.DemandSurveyImgDTO;
import com.massel.www.domain.DemandSurveyImgVO;
import com.massel.www.domain.DemandSurveyParticipationVO;
import com.massel.www.domain.DemandSurveyProductEditDTO;
import com.massel.www.domain.DemandSurveyProductVO;
import com.massel.www.domain.DemandSurveyVO;
import com.massel.www.domain.PagingVO;

public interface DemandSurveyService {
	
	DemandSurveyVO getDSDetail(int dno);

	//============이미지를 함께 불러오기 위한 리스트========
	List<DemandSurveyImgDTO> getList(PagingVO pvo);

	int registerProductList(List<DemandSurveyProductVO> productList, int dno);

	
	//===================섬머노트 글 등록 임시 ================
	int tmpRegister(DemandSurveyVO dsvo);

	int registerDS(DemandSurveyVO dsvo);

	//게시물 등록(이미지 리스트에서 하나로 바뀜 썸네일만 필요해서)
	int registerSurvey(DemandSurveyImgDTO dsidto);

	//썸네일이랑 글 같이 가져오기
	DemandSurveyImgDTO getDetail(int dno);

	//product list 가져오기
	List<DemandSurveyProductVO> getProductList(int dno);

	//product vo 가져오기
	DemandSurveyProductVO getProduct(int dpno);

	int insertDemandParticipation(DemandSurveyParticipationVO dsppvo);

	//UserController에서 한 글쓴이의 수요조사 폼 목록 가져오기 (글쓴이용)
	List<DemandSurveyVO> getDsList(String userID);

	DemandSurveyImgVO getThumbNailFile(int dno);

	//참여자 수 구하기
	int getParticipantCount(int dno);

	//참여 상세정보
	List<DemandSurveyParticipationVO> getParticipation(int dno);

	//나의 참여정보 (참여자용)
	List<DemandSurveyParticipationVO> getMyParticipation(String id);

	//참여정보 디테일(참여자용)
	List<DemandSurveyParticipationVO> getUserDemandFormDetail(String userId, int dno);

	//참여정보 디테일(참여자용) 중복제거
	DemandSurveyParticipationVO getParticipation(String userId, int dno);

	//조회수증가
	int updateReadCount(int dno);

	//회원이 폼에 참여했는지 여부
	int getIsParticipated(String userID, int dno);

	//메인에 가져올 최신순 10개
	List<DemandSurveyImgDTO> getDemandList();

	//int updateDemandSurvey(DemandSurveyImgDTO dsidto);

	int updateProductList(DemandSurveyProductEditDTO editList);

	int updateDemandSurvey(DemandSurveyVO dsvo, DemandSurveyImgVO file);
	
	//demandSurvey delete
	int deleteDemand(int dno);

	List<DemandSurveyImgDTO> getDemandSearchList(String keyword);

	List<DemandSurveyImgDTO> getPopularDemand();

	int getTotalCount(PagingVO pvo);

	int getLastInsertedDno();

}
