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
public class SaleProductVO {

	private int spno;
	private int sno;
	private String spname;
	private int spprice;
	private int maxOrderQuantity;
	private int stock;
}
