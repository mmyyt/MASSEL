package com.massel.www.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SaleSellerVO {

	//판매자의 계좌정보
	private int sno;
	private String swriter;
	private String accountNumber;
	private String accountHolder;
	private String bankName;
}
