package com.massel.www.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.massel.www.domain.SaleOrdererInfoDTO;
import com.massel.www.domain.SaleOrdererVO;

public interface SaleOrdererDAO {

	int insertUserData(SaleOrdererVO saleOrdererData);

	int getMaxOrderNo();

	List<SaleOrdererInfoDTO> getOrderDetail(@Param("sno")int sno, @Param("pendingStatus")String pendingStatus);

	SaleOrdererInfoDTO getOrdererDetail(int orderNo);

	List<SaleOrdererVO> getMyParticipationList(String id);

	SaleOrdererVO getSno(int orderNo);

	int getIsParticipated(@Param("sno")int sno,@Param("userID") String userID);

	//결제대기인 list가져오기
	List<SaleOrdererVO> getOrderList(String status);

	//결제취소로 update하기
	int updateOrderStatus(@Param("orderStatus") String orderStatus,@Param("sno") int sno);
	
	//입금완료로 update하기
	int changeOrderStatus(@Param("realOrderNo")int realOrderNo,@Param("orderStatus") String orderStatus);

	//결제취소 업데이트(유저의 선택)
	int cancleOrder(@Param("realOrderNo") int realOrderNo,@Param("status")String status);

	//유저의 주문상태변경
	int updateBuyerOrder(@Param("orderNo")int orderNo,@Param("orderStatus")String orderStatus);

	int deleteOrderNo(int orderNo);

	int getParticipantCount(int sno);




}
