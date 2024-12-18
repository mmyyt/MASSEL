package com.massel.www.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.massel.www.domain.DemandSurveyDTO;
import com.massel.www.domain.DemandSurveyImgDTO;
import com.massel.www.domain.DemandSurveyImgVO;
import com.massel.www.domain.DemandSurveyParticipationVO;
import com.massel.www.domain.DemandSurveyProductEditDTO;
import com.massel.www.domain.DemandSurveyProductVO;
import com.massel.www.domain.DemandSurveyVO;
import com.massel.www.domain.PagingVO;
import com.massel.www.domain.SaleImgDTO;
import com.massel.www.domain.SaleImgVO;
import com.massel.www.domain.SaleVO;
import com.massel.www.repository.DemandSurveyDAO;
import com.massel.www.repository.DemandSurveyImgDAO;
import com.massel.www.repository.DemandSurveyParticipationDAO;
import com.massel.www.repository.DemandSurveyProductDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DemandSurveyServiceImpl implements DemandSurveyService {

	@Inject
	private DemandSurveyDAO dsdao;
	
	@Inject
	private DemandSurveyImgDAO dsidao;
	
	@Inject
	private DemandSurveyProductDAO dspdao;
	
	@Inject
	private DemandSurveyParticipationDAO dspcdao;
		
	//===============================================
	//수요조사 폼 디테일
	
	@Override
	public DemandSurveyVO getDSDetail(int dno) {
		log.info("수요조사 폼 상세 보기 >>>>"+dno);
		return dsdao.getDSDetail(dno);
	}

	//=============================================================
	
	@Override
	public List<DemandSurveyImgDTO> getList(PagingVO pvo) {
		log.info("@@@ demand survey list service @@@ ");
		
		List<DemandSurveyImgDTO> dsidto = new ArrayList<DemandSurveyImgDTO>();
		
		List<DemandSurveyVO> dsvoList = dsdao.getDSList(pvo);
		log.info("수요 조사 글 목록 >>>>>>>>>>>>>>"+dsvoList.toString());
		for(DemandSurveyVO dsvo : dsvoList ) {
			int dno = dsvo.getDno();
			DemandSurveyImgVO dsivo = dsidao.getDSimgList(dno);
			dsidto.add(new DemandSurveyImgDTO(dsvo, dsivo));
		}
		
		log.info("수요조사 폼 글, 사진 목록 >>>>>>>>>>>>>>>>>"+dsidto.toString());
		
		return dsidto;
	}


	//====================================================
	//수요조사 상품 등록
	@Override
	public int registerProductList(List<DemandSurveyProductVO> productList, int dno) {
		
		int isOk = 1;
		for(DemandSurveyProductVO dsvo : productList) {
			dsvo.setDno(dno);
			isOk *= dspdao.registerProductList(dsvo);
		}
		if(isOk>0) {
			log.info("productList 등록 성공");
		}else {
			log.info("productList 등록 실패");
		}
				
		return isOk;
	}

	//==========================섬머노트 글 임시============================
	
	@Override
	public int tmpRegister(DemandSurveyVO dsvo) {
		log.info("썸머노트 이미지 임시 등록 서비스!!!!!!!!!!!!!");
		return dsdao.tmpRegister(dsvo);
	}

	@Override
	public int registerDS(DemandSurveyVO dsvo) {
		log.info("썸머노트 글 임시 등록 서비수");
		return dsdao.registerDS(dsvo);
	}
	
	//========================글 등록 (글, 썸네일만 등록) ==================

	@Override
	public int registerSurvey(DemandSurveyImgDTO dsidto) {
		log.info("수요조사 폼 객체 + 썸네일 service!");
		
		
		log.info("dsidto 객체 보여주세요 >>> "+dsidto);
		
		int isOk = dsdao.insertDemandSurvey(dsidto.getDsvo()); //dsvo 등록(글객체)
		log.info("*******************        >>    "+dsidto.getDsivo());
		
		if(dsidto.getDsivo() == null) { //파일이 없다는 의미
			return 0;
		}else {
			if(isOk>0) {
				int dno = dsdao.getLastInsertedDno();
				log.info("썸네일 파일에 들어갈 글번호 >> "+dno);

				DemandSurveyImgVO dsivo = dsidto.getDsivo();
				dsivo.setDno(dno);
				isOk *= dsidao.registerDSImg(dsivo);
			}
		}
		return isOk;
	}


	//썸네일이랑 글 같이 가져오기
	@Override
	public DemandSurveyImgDTO getDetail(int dno) {
		log.info("썸네일이랑 글 같이 가져오기 service!");
		
		//각각 가져와서 집어넣기
		DemandSurveyVO dsvo = dsdao.getDSDetail(dno);
		DemandSurveyImgVO dsivo = dsidao.getDSimgList(dno);
		
		DemandSurveyImgDTO dsidto = new DemandSurveyImgDTO(dsvo, dsivo);
		
		return dsidto;
	}


	//product list 가꼬오기
	@Override
	public List<DemandSurveyProductVO> getProductList(int dno) {
		log.info("product List service" );
		return dspdao.getProductList(dno);
	}


	//product 객체 하나 가꼬오기
	@Override
	public DemandSurveyProductVO getProduct(int dpno) {
		log.info("productVO service!");
		return dspdao.getProduct(dpno);
	}


	//수요조사 참여 insert
	@Override
	public int insertDemandParticipation(DemandSurveyParticipationVO dsppvo) {
		log.info("demandParticipation service!");
		return dspcdao.insertDemandParticipation(dsppvo);
	}


	//userController로 보낼거 (수요조사 목록)
	@Override
	public List<DemandSurveyVO> getDsList(String userID) {
		// TODO Auto-generated method stub
		return dsdao.getDsList(userID);
	}


	@Override
	public DemandSurveyImgVO getThumbNailFile(int dno) {
		// TODO Auto-generated method stub
		return dsidao.getThumbnailFile(dno);
	}


	//참가자 수 가져오기
	@Override
	public int getParticipantCount(int dno) {
		
		return dspcdao.getParticipantCount(dno);
	}


	//참여상세정보
	@Override
	public List<DemandSurveyParticipationVO> getParticipation(int dno) {
		// TODO Auto-generated method stub
		return dspcdao.getParticipation(dno);
	}

	//수요조사 참여자의 참여내역
	@Override
	public List<DemandSurveyParticipationVO> getMyParticipation(String id) {
		// TODO Auto-generated method stub
		return dspcdao.getMyParticipation(id);
	}

	//수요조사 참여자의 참여내역 디테일
	@Override
	public List<DemandSurveyParticipationVO> getUserDemandFormDetail(String userId, int dno) {
		// TODO Auto-generated method stub
		return dspcdao.getUserDemandFormDeatil(userId, dno);
	}


	@Override
	public DemandSurveyParticipationVO getParticipation(String userId, int dno) {
		// TODO Auto-generated method stub
		return dspcdao.getParticipationInfo(userId, dno);
	}

	//조회수증가
	@Override
	public int updateReadCount(int dno) {
		// TODO Auto-generated method stub
		return dsdao.updateReadCount(dno);
	}

	//회원이 폼에 참여했는지 여부
	@Override
	public int getIsParticipated(String userID, int dno) {
		
		return dspcdao.isParticipated(userID, dno);
	}


	//메인에 가져올 최신순 10개
	@Override
	public List<DemandSurveyImgDTO> getDemandList() {
		
		List<DemandSurveyImgDTO> list = new ArrayList<DemandSurveyImgDTO>();
		
		List <DemandSurveyVO> dsvoList = dsdao.getDemandSurvey();
		
		for(DemandSurveyVO dsvo : dsvoList) {
			int dno = dsvo.getDno();
			DemandSurveyImgVO dsivo = dsidao.getThumbnailFile(dno);
			
			list.add(new DemandSurveyImgDTO(dsvo,dsivo));
		}
		
		return list;
	}


	//productList update
	@Override
	public int updateProductList(DemandSurveyProductEditDTO editList) {
		
		List<DemandSurveyProductVO> productList = editList.getProductList();
		log.info("수정할 productList >>> "+productList);
		
		//기존제품 삭제
		int dno = editList.getDno();
		
		int isOk = dspdao.deleteOriginal(dno);
		
		//제품 집어넣기
		for(DemandSurveyProductVO dspvo : productList) {
			dspvo.setDno(dno);
			isOk *= dspdao.registerProductList(dspvo);
		}
		
		return isOk;
	}


	@Override
	public int updateDemandSurvey(DemandSurveyVO dsvo, DemandSurveyImgVO file) {
		
		int dno = dsvo.getDno();
		
		log.info("변경할 글 번호 >> "+dno);
		
		int isOk = dsdao.updateDemandSurvey(dsvo);
		isOk = dsidao.updateDemandSurveyImg(file, dno);
		
		return isOk;
	}


	@Override
	public int deleteDemand(int dno) {
		
		int isOk = dsdao.deleteDemand(dno);
		
		return isOk;
	}


	@Override
	public List<DemandSurveyImgDTO> getDemandSearchList(String keyword) {
		
		List<DemandSurveyImgDTO> demandList = new ArrayList<DemandSurveyImgDTO>();
		
		List<DemandSurveyVO> list = dsdao.getDemandSurveySearch(keyword);
		for(DemandSurveyVO dsvo : list) {
			int dno = dsvo.getDno();
			DemandSurveyImgVO dsivo = dsidao.getThumbnailFile(dno);
			
			demandList.add(new DemandSurveyImgDTO(dsvo, dsivo));
		}
		
		if(list.size()==0) {
			return null;
		}
		return demandList;
	}


	@Override
	public List<DemandSurveyImgDTO> getPopularDemand() {
		
		log.info("수요조사 폼 인기순 !");
		List<DemandSurveyImgDTO> popularDemand = new ArrayList<DemandSurveyImgDTO>();
		
		List<DemandSurveyVO> list = dsdao.getPopularList();
		for(DemandSurveyVO dsvo : list) {
			int dno = dsvo.getDno();
			DemandSurveyImgVO dsivo = dsidao.getThumbnailFile(dno);
			
			popularDemand.add(new DemandSurveyImgDTO(dsvo, dsivo));
		}
		return popularDemand;
	}


	@Override
	public int getTotalCount(PagingVO pvo) {
		// TODO Auto-generated method stub
		return dsdao.getTotalCount(pvo);
	}

	@Override
	public int getLastInsertedDno() {
		// TODO Auto-generated method stub
		return dsdao.getLastInsertedDno();
	}



	

}
