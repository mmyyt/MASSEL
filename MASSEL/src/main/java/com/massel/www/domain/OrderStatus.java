package com.massel.www.domain;

public enum OrderStatus {

	PENDING_PAYMENT("결제 대기"),
	PAYMENT_CANCELED("결제 취소"),
	PAYMENT_COMPLETED("결제 완료"),
	PAYMENT_CONFIRMED("결제 확인"),
	PREPARING_SHIPMENT("배송 준비"),
	IN_TRANSIT("배송 중"),
	DELIVERED("배송 완료"),
	TRANSACTION_COMPLETED("거래 종료");
	
	private final String displayName;
	
	OrderStatus(String displayName) {
		this.displayName = displayName;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
}
