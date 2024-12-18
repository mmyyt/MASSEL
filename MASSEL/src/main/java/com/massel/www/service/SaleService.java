package com.massel.www.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.massel.www.domain.PagingVO;
import com.massel.www.domain.SaleDTO;
import com.massel.www.domain.SaleFavoriteVO;
import com.massel.www.domain.SaleImgDTO;
import com.massel.www.domain.SaleImgVO;
import com.massel.www.domain.SaleOrdererInfoDTO;
import com.massel.www.domain.SaleOrdererParticipationDTO;
import com.massel.www.domain.SaleOrdererVO;
import com.massel.www.domain.SaleProductDTO;
import com.massel.www.domain.SaleProductImgVO;
import com.massel.www.domain.SaleProductVO;
import com.massel.www.domain.SaleSellerVO;
import com.massel.www.domain.SaleShipmentVO;
import com.massel.www.domain.SaleVO;

public interface SaleService {

	//글등록
	int registerSale(SaleImgDTO sidto);

	//상품등록
	int registerSaleProduct(List<SaleProductVO> productList, List<MultipartFile> imageFiles, int sno);

	//리스트
	List<SaleImgDTO> getList();

	//배송방법등록
	int registerSaleShipping(List<SaleShipmentVO> shippingList, int sno);

	//글가져오기
	SaleVO getDetail(int sno);

	//썸네일가져오기
	SaleImgVO getThumbnail(int sno);
	
	//제품목록 가져오기
	List<SaleProductDTO> getProductList(int sno);

	//배송방법목록 가져오기
	List<SaleShipmentVO> getShipment(int sno);

	//계좌정보 가져오기
	SaleSellerVO getSellerAccount(int sno);

	//상품가져오기(하나)
	SaleProductVO getProduct(int spno);

	//pdto << 유저의 상품 구매 정보
	int insertUserSaleInfo(SaleOrdererParticipationDTO pdto);

	//user favorite insert하기~
	int insertFavorite(String userID, int sno);

	int deleteFavorite(String userID, int sno);

	//좋아요수 업데이트
	int updateFavoriteCount(int sno, int count);

	//현재 좋아요수
	int getFavoriteCount(int sno);

	//해당 사용자가 좋아요를 눌렀는지 확인
	int isFavorite(int sno, String userID);

	//조회수 증가
	int readCount(int sno);

	//글쓴이의 판매 폼 작성 리스트 가져오기
	List<SaleVO> getMySaleList(String userID);

	//참가자 수 구하기
	int getParticipationCount(int sno);

	//판매폼 참여자 정보 가져오기
	List<SaleOrdererInfoDTO> getOrderDetail(int sno, String pendingStatus);

	//판매폼 참여자 개인 정보 가져오기
	SaleOrdererInfoDTO getOrdererDetail(int orderNo);

	//상품 구매- 유저의 참여 리스트 가져오기
	List<SaleOrdererVO> getMyParticipationList(String id);

	SaleOrdererVO getSno(int orderNo);

	//사용자가 참여했는지 여부 확인
	int getIsParticipated(int sno, String userID);
	
	//판매자 계좌정보
	int insertSellerAccount(SaleSellerVO sellerInfo);

	int cancleOrder(int realOrderNo, String status);

	//판매자가 유저주문상태 변경
	int updateBuyerOrder(int orderNo, String orderStatus);

	//판매자가 유저주문삭제
	int deleteOrderNo(int orderNo);

	//조회수 높은거 가져오기
	List<SaleImgDTO> getPopularList();
	
	//찜순위 높은거 가져오기
	List<SaleImgDTO> getFavoriteList();

	//상품폼 최신순
	List<SaleImgDTO> getLatestList();

	//상품 이미지 가져오기
	SaleProductImgVO getProductImg(int spno);

	//정보 수정을 위해 모든 값 가져오기
	SaleDTO getSaleDetail(int sno);

	//참가자 수 구하기(sale_orderer에서)
	int getParticipantCount(int sno);
	
	//글,썸네일 수정
	int updateSale(SaleVO svo, SaleImgVO file);
	
	//상품 update
	int updateProduct(List<SaleProductVO> productList, List<MultipartFile> imageFiles, int sno);

	//배송정보 update
	int updateShipment(List<SaleShipmentVO> shippingList);

	int updateSellerInfo(String bankName, String accountNumber, String accountHolder, int sno);
	
	//글삭제
	int deleteSale(int sno);
	
	//글 영구삭제
	int removeSale(int sno);

	//검색어
	List<SaleImgDTO> getSaleSearchList(String keyword);
	
	//해당 유저의 모든 찜목록
	List<SaleFavoriteVO> myFavoriteList(String userId);
	
	//유저의 favorite list를 위한 글 리스트
	SaleVO getSvoDetail(int sno);

	//sale list가져오기
	//List<SaleImgDTO> getSales(Long cursorId, int pageSize);

	List<SaleImgDTO> getPagingList(PagingVO pvo);

	int getTotalCount(PagingVO pvo);

	int getLastInsertedSno();


}
