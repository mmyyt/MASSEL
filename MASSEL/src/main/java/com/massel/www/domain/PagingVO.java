package com.massel.www.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class PagingVO {
	
	private int pageNo; //현재 페이지가 몇번째 페이지인지
	private int qty; //한 페이지에 들어있는 총 게시물 수
	
	public PagingVO() {
		this(1, 20); //한 페이지에 20개의 글
	}
	
	public PagingVO(int pageNo, int qty) {
		this.pageNo = pageNo;
		this.qty = qty;
	}
	
	public int getStartPage() {
		return (this.pageNo-1)*qty;
	}
}
