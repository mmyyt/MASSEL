package com.massel.www.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SaleOrdererInfoDTO {
	
	private int orderNo;
	private String id;
	private String ordererName;
	private String ordererPhone;
	private String ordererEmail;
	private String recipientName;
	private String recipientPhoneNumber;
	private String recipientPostalCode;
	private String recipientAddress;
	private String recipientDetailAddress;
	private String shippingMethod;
	private int shippingCost;
	private String shippingNote;
	private String products;
	private int totalPrice;
	private String refundAccount;
	private String refundBank;
	private String refundAccountHolder;
	private String orderDate;
	private int productPrice;
	private String orderStatus;
	
}
