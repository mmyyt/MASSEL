package com.massel.www.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.massel.www.domain.PagingVO;
import com.massel.www.domain.SaleVO;

public interface SaleDAO {

	int registerSale(SaleVO svo);

	int getSno();

	List<SaleVO> getList();

	SaleVO getSaleDetail(int sno);

	//좋아요 수 업데이트
	int updateFavoriteCount(@Param("sno")int sno,@Param("count")int count);

	//좋아요 수 가져오기
	int getFavoriteCount(int sno);

	//조회수
	int updateReadCount(int sno);

	//판매폼 리스트 가쟈오기
	List<SaleVO> getMySaleList(String userID);

	//cron식을 위한 판매폼 리스트
	List<SaleVO> getSaleFormList(String endStatus);

	//status update
	int updateStatus(@Param("sno")int sno,@Param("status") String status);

	List<SaleVO> getPopularSvo();

	List<SaleVO> getFavoriteList();

	List<SaleVO> getLatestList();

	int updateSale(SaleVO svo);

	//판매글삭제
	int deleteSale(int sno);
	//글영구삭제
	int removeSale(int sno);

	List<SaleVO> getSaleSearchList(String keyword);

	SaleVO getSvoDetail(int sno);

	//페이징 리스트 가져오기
	List<SaleVO> getPagingList(PagingVO pvo);

	int getTotalCount(PagingVO pvo);

	int getLastInsertedSno();



}
