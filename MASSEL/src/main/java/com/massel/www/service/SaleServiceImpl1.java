package com.massel.www.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.massel.www.domain.PagingVO;
import com.massel.www.domain.SaleDTO;
import com.massel.www.domain.SaleFavoriteVO;
import com.massel.www.domain.SaleImgDTO;
import com.massel.www.domain.SaleImgVO;
import com.massel.www.domain.SaleOrdererInfoDTO;
import com.massel.www.domain.SaleOrdererParticipationDTO;
import com.massel.www.domain.SaleOrdererProductVO;
import com.massel.www.domain.SaleOrdererShipment;
import com.massel.www.domain.SaleOrdererVO;
import com.massel.www.domain.SaleProductDTO;
import com.massel.www.domain.SaleProductImgVO;
import com.massel.www.domain.SaleProductVO;
import com.massel.www.domain.SaleSellerVO;
import com.massel.www.domain.SaleShipmentVO;
import com.massel.www.domain.SaleVO;
import com.massel.www.handler.SaleProductImgFileHandler;
import com.massel.www.repository.SaleDAO;
import com.massel.www.repository.SaleFavoriteDAO;
import com.massel.www.repository.SaleImgDAO;
import com.massel.www.repository.SaleOrdererDAO;
import com.massel.www.repository.SaleOrdererProductDAO;
import com.massel.www.repository.SaleOrdererShipmentDAO;
import com.massel.www.repository.SaleProductDAO;
import com.massel.www.repository.SaleProductImgDAO;
import com.massel.www.repository.SaleSellerDAO;
import com.massel.www.repository.SaleShipmentDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SaleServiceImpl1 implements SaleService {

	@Inject
	private SaleDAO sdao;
	
	@Inject
	private SaleImgDAO sidao;
	
	@Inject
	private SaleProductDAO spdao;
	
	@Inject
	private SaleShipmentDAO ssdao;
	
	@Inject
	private SaleSellerDAO sldao;
	
	@Inject
	private SaleOrdererProductDAO sopdao;
	
	@Inject
	private SaleOrdererDAO sodao;
	
	@Inject
	private SaleOrdererShipmentDAO sosdao;
	
	@Inject
	private SaleFavoriteDAO sfdao;
	
	@Inject
	private SaleProductImgFileHandler sphd;
	
	@Inject
	private SaleProductImgDAO spidao;
	
	@Override
	public int registerSale(SaleImgDTO sidto) {
		log.info("sale register service");
		int isOk = sdao.registerSale(sidto.getSvo());
		
		if(sidto.getSivo() == null) {
			return 0;
		}else {
			if(isOk>0) {	
				//sno 가져오기
				int sno = sdao.getLastInsertedSno();
				log.info("글번호 >> "+sno);
				SaleImgVO sivo = sidto.getSivo();
				sivo.setSno(sno);
				isOk *= sidao.registerSaleImg(sivo);
			}
		}
		return isOk;
	}

	@Override
	public int registerSaleProduct(List<SaleProductVO> productList, List<MultipartFile> imageFiles, int sno) {
		
		log.info("상품등록 service >>> sno >>> "+sno);
		int isOk = 0;
		
		for(int i = 0; i<productList.size(); i++) {
			
			//SaleProductVO spvo = productList[i];
			//List에서 인덱스를 사용해 요소를 가져올땐 get을 이용해야함
			
			SaleProductVO spvo = productList.get(i);
			log.info("하나의 product >>> "+spvo.toString());
			spvo.setSno(sno);
			
			isOk = spdao.registerSaleProduct(spvo);
			
			log.info((isOk>0)? "상품 집어넣기 성공 ": "상품 집어넣기 실패");
			
			if(isOk>0 && imageFiles!=null && !imageFiles.isEmpty()) {
				//상품집어넣기가 성공했고 이미지 파일이 있으면 집어넣기
				if(imageFiles.get(i) != null) {
					//해당 인덱스의 상품 이미지가 있으면 집어넣기
					log.info("image 설정");
					//imageFile 핸들러로 보내서 설정하기
					MultipartFile imageFile = imageFiles.get(i);
					SaleProductImgVO file = sphd.uploadImg(imageFile);
					
					//방금 insert된 spno를 가지고 파일도 업로드 해주기.
					int spno = spdao.getLastSpno();
					log.info("마지막으로 insert 된 spno >>> "+spno);
					
					file.setSno(sno);
					file.setSpno(spno);
					
					log.info("상품 이미지 집어넣기@@@@@@@@@@@@@@@@@@@@@");
					isOk = spidao.registerProductImg(file);
					
					log.info((isOk>0)?"상품 이미지 집어넣기 성공":"상품 이미지 집어넣기 실패");
					
				}								
			}
		}
		
		return isOk;
	}

	//리스트
	@Override
	public List<SaleImgDTO> getList() {
		log.info("sale List service");
		
		List<SaleImgDTO> sidto = new ArrayList<SaleImgDTO>();
		List<SaleVO> svoList = sdao.getList();
		for(SaleVO svo : svoList) {
			int sno = svo.getSno();
			SaleImgVO sivo = sidao.getImgList(sno);
			sidto.add(new SaleImgDTO(svo, sivo));
		}
		
		return sidto;		
	}

	//배송방법등록
	@Override
	public int registerSaleShipping(List<SaleShipmentVO> shippingList, int sno) {
		log.info("배송방법 등록 service >>> sno >>> "+sno);
		int isOk = 1;
		for(SaleShipmentVO ssvo : shippingList) {
			ssvo.setSno(sno);
			isOk *= ssdao.registerSaleShipping(ssvo);
		}
		
		return isOk;
	}
	
	//계좌등럭
	@Override
	public int insertSellerAccount(SaleSellerVO sellerInfo) {
		int isOk = sldao.insertSellerAccount(sellerInfo);
		return isOk;
	}
	

	//글가져오기
	@Override
	public SaleVO getDetail(int sno) {
		log.info("saleVO detail 가져오기!");
		return sdao.getSaleDetail(sno);
	}

	//썸네일 가져오기
	@Override
	public SaleImgVO getThumbnail(int sno) {
		log.info("saleImgVO detail 가져오기!");
		return sidao.getImgList(sno);
	}

	//제품목록 가져오기
	@Override
	public List<SaleProductDTO> getProductList(int sno) {
		log.info("saleProduct list 가져오기!");
		
		List<SaleProductDTO> list = new ArrayList<SaleProductDTO>();
		
		//상품
		List<SaleProductVO> productList = spdao.getProductList(sno);
		
		for(SaleProductVO spvo : productList) {
			int spno = spvo.getSpno();
			
			SaleProductImgVO spivo = spidao.getProductImg(spno);
			
			list.add(new SaleProductDTO(spvo, spivo));
		}
		
		log.info("saleProductList 값!!! >>>> "+list.toString());
		
		return list;
}

	//배송방법 목록 가져오기
	@Override
	public List<SaleShipmentVO> getShipment(int sno) {
		log.info("shipment method list 가져오기!");
		return ssdao.getShipmentMethod(sno);
	}

	//글쓴이계좌정보
	@Override
	public SaleSellerVO getSellerAccount(int sno) {
		log.info("판매자 계좌정보 가져오기");
		return sldao.getSellerAccount(sno);
	}
	
	//상품가져오기(하나)
	@Override
	public SaleProductVO getProduct(int spno) {
		log.info("상품 가쟈ㅕ오기~");
		return spdao.getProduct(spno);
	}

	//유저의 상품 구매 정보
	@Override
	public int insertUserSaleInfo(SaleOrdererParticipationDTO pdto) {
		
		List<SaleOrdererProductVO> userProductList = pdto.getUserProductList();
		SaleOrdererVO saleOrdererData = pdto.getSaleOrdererData();
		SaleOrdererShipment saleOrdererShippingData = pdto.getSaleOrdererShippingData();
		
		log.info(userProductList.toString());
		log.info(saleOrdererData.toString());
		log.info(saleOrdererShippingData.toString());
		
		int productResult = 1;
		int updateStockResult = 1;
		int ordererShipmentResult = 0;
		//상품 order_no를 똑같은 번호로 설정해주기
		//현재 order_no 최대 번호 구하기

		int orderNo = sodao.getMaxOrderNo();
		log.info("현재 최대 orderNo >>> "+orderNo);
		orderNo++;
		
		saleOrdererData.setOrderNo(orderNo);
		int ordererResult = sodao.insertUserData(saleOrdererData); 
		
		if(ordererResult > 0) {
			for(SaleOrdererProductVO sopvo : userProductList) { //상품1
				sopvo.setOrderNo(orderNo);
				productResult *= sopdao.insertUserProduct(sopvo);
				
				//상품 재고 관리(count만큼 해당 상품 재고 관리하기)
				int spno = sopvo.getSpno();
				int count = sopvo.getCount();
				
				updateStockResult *= spdao.updateStock(spno, count);
			}

			saleOrdererShippingData.setOrderNo(orderNo);
			ordererShipmentResult = sosdao.insertUserShipment(saleOrdererShippingData);
		}

		
		return (productResult>0 && updateStockResult>0 && ordererResult>0 && ordererShipmentResult>0) ? 1: 0;

	}

	//유저 좋아요 추가
	@Override
	public int insertFavorite(String userID, int sno) {
		// TODO Auto-generated method stub
		return sfdao.insertFavorite(userID, sno);
	}

	//유저 좋아요 삭제
	@Override
	public int deleteFavorite(String userID, int sno) {
		// TODO Auto-generated method stub
		return sfdao.deleteFavorite(userID, sno);
	}

	//좋아요 수 업뎃
	@Override
	public int updateFavoriteCount(int sno, int count) {
		
		return sdao.updateFavoriteCount(sno, count);
	}

	//현재 좋아요 수
	@Override
	public int getFavoriteCount(int sno) {

		return sdao.getFavoriteCount(sno);
	}

	//로그인 사용자가 좋아요 눌렀는지 확인
	@Override
	public int isFavorite(int sno, String userID) {
		// TODO Auto-generated method stub
		return sfdao.isFavorite(sno, userID);
	}

	//조회수 증가
	@Override
	public int readCount(int sno) {
		// TODO Auto-generated method stub
		return sdao.updateReadCount(sno);
	}

	//작성자의 판매폼 리스트 가져오기
	@Override
	public List<SaleVO> getMySaleList(String userID) {
		// TODO Auto-generated method stub
		return sdao.getMySaleList(userID);
	}

	//참여자 수 구하기
	@Override
	public int getParticipationCount(int sno) {
		// TODO Auto-generated method stub
		return sopdao.getParticipationCount(sno);
	}

	//판매폼 참여자 디테일 정보
	@Override
	public List<SaleOrdererInfoDTO> getOrderDetail(int sno, String pendingStatus) {
		// TODO Auto-generated method stub
		return sodao.getOrderDetail(sno, pendingStatus);
	}

	//판매폼 참여자 개인 디테일 정보
	@Override
	public SaleOrdererInfoDTO getOrdererDetail(int orderNo) {
		// TODO Auto-generated method stub
		return sodao.getOrdererDetail(orderNo);
	}

	//상품구매 - 구매자의 리스트가져오기
	@Override
	public List<SaleOrdererVO> getMyParticipationList(String id) {
		
		return sodao.getMyParticipationList(id);
	}

	@Override
	public SaleOrdererVO getSno(int orderNo) {
		// TODO Auto-generated method stub
		return sodao.getSno(orderNo);
	}

	@Override
	public int getIsParticipated(int sno, String userID) {
		// TODO Auto-generated method stub
		return sodao.getIsParticipated(sno, userID);
	}

	//결제완료로 상태 변경하기
	public int updateOrderStatus(int realOrderNo, String orderStatus) {
		log.info("결제 완료로 상태 변경 service!");
		return sodao.changeOrderStatus(realOrderNo, orderStatus);
	}

	@Override
	public int cancleOrder(int realOrderNo, String status) {
		log.info("결제취소로 상태변경");
		return sodao.cancleOrder(realOrderNo,status);
	}

	@Override
	public int updateBuyerOrder(int orderNo, String orderStatus) {
		log.info("유저의 주문상태 변경");
		return sodao.updateBuyerOrder(orderNo, orderStatus);
	}

	@Override
	public int deleteOrderNo(int orderNo) {
		log.info("유저 주문 삭제");
		return sodao.deleteOrderNo(orderNo);
	}

	//조회수 높은거가져오기
	@Override
	public List<SaleImgDTO> getPopularList() {
		
		List<SaleImgDTO> list = new ArrayList<SaleImgDTO>();
		
		//most popular svo 가져오기
		List<SaleVO> svoList = sdao.getPopularSvo();
		
		for(SaleVO svo : svoList ) {
			int sno = svo.getSno();
			SaleImgVO sivo = sidao.getImgList(sno);
			
			list.add(new SaleImgDTO(svo, sivo));
		}

		return list;
	}

	@Override
	public List<SaleImgDTO> getFavoriteList() {
		
		List<SaleImgDTO> list = new ArrayList<SaleImgDTO>();
		
		List<SaleVO> svoList = sdao.getFavoriteList();
		
		for(SaleVO svo : svoList) {
			int sno = svo.getSno();
			SaleImgVO sivo = sidao.getImgList(sno);
			
			list.add(new SaleImgDTO(svo, sivo));
		}
		return list;
	}

	@Override
	public List<SaleImgDTO> getLatestList() {
		
		List<SaleImgDTO> list = new ArrayList<SaleImgDTO>();
		
		List<SaleVO> svoList = sdao.getLatestList(); 
		
		for(SaleVO svo : svoList) {
			int sno = svo.getSno();
			SaleImgVO sivo = sidao.getImgList(sno);
			
			list.add(new SaleImgDTO(svo, sivo));
		}
		
		return list;
	}

	@Override
	public SaleProductImgVO getProductImg(int spno) {
		log.info("상품 이미지 가져오기");
		return spidao.getProductImg(spno);
	}

	@Override
	public SaleDTO getSaleDetail(int sno) {
		
	
		//글, 썸넬
		SaleVO svo = sdao.getSaleDetail(sno);
		SaleImgVO sivo = sidao.getImgList(sno);
		
		//상품정보
		List<SaleProductDTO> productList = new ArrayList<SaleProductDTO>();
		
		List<SaleProductVO> spvoList = spdao.getProductList(sno);
		for(SaleProductVO product : spvoList) {
			int spno = product.getSpno();
			SaleProductImgVO spivo = spidao.getProductImg(spno);
			
			productList.add(new SaleProductDTO(product, spivo));
		}
				
		//배송정보
		List<SaleShipmentVO> shipmentList = ssdao.getShipmentMethod(sno);
		
		//계좌정보
		SaleSellerVO ssvo = sldao.getSellerAccount(sno);
		
		SaleDTO dto = new SaleDTO(svo, sivo, ssvo, productList, shipmentList);

		return dto;
	}

	@Override
	public int getParticipantCount(int sno) {
		log.info("참가자 수 구하기");
		return sodao.getParticipantCount(sno);
	}

	//글, 썸네일 수정
	@Override
	public int updateSale(SaleVO svo, SaleImgVO file) {
		int sno = svo.getSno();
		
		log.info("변경할 글 번호 >> "+sno);
		
		int isOk = sdao.updateSale(svo);
		isOk = sidao.updateSaleImg(file, sno);
		
		return isOk;
	}

	@Override
	public int updateProduct(List<SaleProductVO> productList, List<MultipartFile> imageFiles, int sno) {
		//기존 상품 삭제
		int isOk = spdao.deleteProduct(sno);
		
		//상품 insert
		for(int i=0; i<productList.size(); i++) {
			SaleProductVO spvo = productList.get(i);
			spvo.setSno(sno);
			isOk = spdao.registerSaleProduct(spvo);
			
			log.info((isOk>0)?"상품 insert 성공":"상품 insert 실패");
			
			//이미지 insert
			if(isOk>0) {
				MultipartFile imageFile = imageFiles.get(i);
				SaleProductImgVO spivo = sphd.uploadImg(imageFile);
				
				int spno = spdao.getLastSpno();
				spivo.setSpno(spno);
				spivo.setSno(sno);
				
				isOk = spidao.registerProductImg(spivo);
				
				log.info((isOk>0)?"상품이미지 insert 성공":"상품이미지 insert 실패");
			}
		}
		
		return isOk;
	}

	@Override
	public int updateShipment(List<SaleShipmentVO> shippingList) {
		
		//sno 가져오기
		int sno = 0;
		if (!shippingList.isEmpty()) {
		    sno = shippingList.get(0).getSno(); // 첫 번째 항목의 sno 값 가져오기
		}
		
		log.info("가져온 shippingList sno >> "+sno);
		
		//기존 배송방법삭제
		int isOk = ssdao.deleteShipment(sno);
		log.info((isOk>0)?"배송방법 delete성공":"배송방법 delete실패");
		if(isOk>0) {
			//다시 insert
			for(SaleShipmentVO ssvo : shippingList) {
				ssvo.setSno(sno);
				isOk *= ssdao.registerSaleShipping(ssvo);
				
			}
		}
		
		log.info((isOk>0)?"배송방법 register성공":"배송방법 register실패");
		
		return isOk;
	}

	@Override
	public int updateSellerInfo(String bankName, String accountNumber, String accountHolder, int sno) {
		
		int isOk = sldao.updateSellerInfo(bankName, accountNumber, accountHolder, sno);
		log.info((isOk>0)?"계좌변경성공":"계좌변경실패");
		return isOk; 
	}

	@Override
	public int deleteSale(int sno) {
		
		int isOk = sdao.deleteSale(sno);
		
		if(isOk>0) {
			log.info(sno+" 번 판매 폼 is_del 1로 update");

		}
		
		return isOk;
	}

	@Override
	public int removeSale(int sno) {
		
		int isOk = sdao.removeSale(sno);
		
		if(isOk>0) {
			log.info(sno+" 번 판매 폼 삭제");
			
			//상품삭제
			isOk = spdao.deleteProduct(sno);
			//배송보삭제
			isOk = ssdao.deleteShipment(sno);
			//계좌정보 삭제
			isOk = sldao.deleteSellerInfo(sno);
		}
		
		return isOk;
	}

	@Override
	public List<SaleImgDTO> getSaleSearchList(String keyword) {
	
		List<SaleImgDTO> saleList = new ArrayList<SaleImgDTO>();
		
		List<SaleVO> list = sdao.getSaleSearchList(keyword);
		
		for(SaleVO svo : list) {
			int sno = svo.getSno();
			SaleImgVO sivo = sidao.getImgList(sno);
			
			saleList.add(new SaleImgDTO(svo, sivo));
		}
		if(saleList.size() == 0) {
			return null;
		}
		return saleList;
	}

	@Override
	public List<SaleFavoriteVO> myFavoriteList(String userId) {
		
		return sfdao.myFavoriteList(userId);
	}

	@Override
	public SaleVO getSvoDetail(int sno) {
		
		return sdao.getSvoDetail(sno);
	}

	//페이징적용한 리스트
	@Override
	public List<SaleImgDTO> getPagingList(PagingVO pvo) {
		List<SaleImgDTO> list = new ArrayList<SaleImgDTO>();
		
		List<SaleVO> saleList = sdao.getPagingList(pvo);
		for(SaleVO svo : saleList) {
			int sno = svo.getSno();
			SaleImgVO sivo = sidao.getImgList(sno);
			
			list.add(new SaleImgDTO(svo, sivo));
		}
		return list;
	}

	@Override
	public int getTotalCount(PagingVO pvo) {
		// TODO Auto-generated method stub
		return sdao.getTotalCount(pvo);
	}

	@Override
	public int getLastInsertedSno() {
		// TODO Auto-generated method stub
		return sdao.getLastInsertedSno();
	}
	
	


}
