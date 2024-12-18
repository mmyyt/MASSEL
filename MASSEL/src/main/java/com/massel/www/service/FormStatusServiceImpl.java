package com.massel.www.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.inject.Inject;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.massel.www.domain.DemandSurveyVO;
import com.massel.www.domain.OrderStatus;
import com.massel.www.domain.SaleOrdererVO;
import com.massel.www.domain.SaleVO;
import com.massel.www.repository.DemandSurveyDAO;
import com.massel.www.repository.SaleDAO;
import com.massel.www.repository.SaleOrdererDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FormStatusServiceImpl{
	
	@Inject
	private DemandSurveyDAO dsdao;
	
	@Inject
	private SaleDAO sdao;
	
	@Inject
	private SaleOrdererDAO sodao;

	// 초 분 시 일 월 요일 (연도)  
	@Scheduled(cron = "0 */10 * * * *")
	public void updateStatus() {
		
		log.info("크론식 실행@@@ status 업데이트 시작합니다");
		
		LocalDateTime now = LocalDateTime.now();
		String endStatus = "ended";
		//demandForm과 saleForm 글 중 상태가 '종료됨'을 제외하고 모두 가져오기
		List<DemandSurveyVO> dsList = dsdao.getDsFormList(endStatus);
		List<SaleVO> sList = sdao.getSaleFormList(endStatus);
	
		int isOk = 0;
		//now startDate isBefore <- 인자보다 과거 (시작전) 이건 필요X 
		//now startDate isAfter <- 인자보다 미래(진행중)
		//now endDate isAfter <- 인자보다 미래 (종료)
		//now endDate isBefor <- 인자보다 과거 진행중 이건 필요X
		for(DemandSurveyVO dsvo : dsList) {
			if(now.isAfter(dsvo.getEndDate())) { 
				dsvo.setStatus("ended");
				isOk = dsdao.updateStatus(dsvo.getDno(), dsvo.getStatus());
			}else if(now.isAfter(dsvo.getStartDate())) {  
				dsvo.setStatus("ongoing");
				isOk = dsdao.updateStatus(dsvo.getDno(), dsvo.getStatus());
			}
		}
		
		for(SaleVO svo : sList) {
			if(now.isAfter(svo.getEndDate())) {
				svo.setStatus("ended"); 
				isOk = sdao.updateStatus(svo.getSno(), svo.getStatus());
			}else if(now.isAfter(svo.getStartDate())) {
				svo.setStatus("ongoing");
				
				isOk = sdao.updateStatus(svo.getSno(), svo.getStatus());
			}
		}

	}
	
	//sale_orderer에 orderStatus가 "결제대기"인 상품 중에 order_date 가 현재시간보다 1시간 지났으면 결제 취소로 상태 바꾸기
	@Scheduled(cron = "0 */10 * * * *")
	public void checkOrderStatus() {
		
		//saleOrderer에 orderStatus가 결제대기인 상품 가져오기
		String status = OrderStatus.PENDING_PAYMENT.name();
		List<SaleOrdererVO> list = sodao.getOrderList(status);
		
		//DateTimeFormatter 설정하기
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		//현재시간 구하기
		LocalDateTime now = LocalDateTime.now();
		
		//list의 orderDate가 now를 기준으로 1시간이 지났는지 확인하기
		for(SaleOrdererVO sovo : list) {
			//orderDate -> String이라서 LocalDateTime으로 변환시켜주기
			LocalDateTime orderDate =  LocalDateTime.parse(sovo.getOrderDate(), formatter);
			
			//현재시간과 orderDate 비교하기(java.time)
			Duration duration = Duration.between(orderDate, now);
			if(duration.toHours() >= 1) {
				log.info("한시간 이상 지남");
				//한시간 이상 지났으면 결제취소로 변경하기
				String orderStatus = OrderStatus.PAYMENT_CANCELED.name();
				int isOk = sodao.updateOrderStatus(orderStatus, sovo.getSno());
				log.info((isOk>0)?"상태변경 성공":"상태변경 실패");
			}
		}
	}

}
