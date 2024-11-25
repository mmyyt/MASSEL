package com.massel.www.domain;

import java.time.LocalDateTime;

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
public class DemandSurveyVO {
	
	private int dno;
	private String dtitle;
	private String ddetail;
	private String id;
	private int dviewCount;
	private int disDel;  //default 0
	private String disEnd;  //default false 
	private String dregDate;
	private int dparticipateCount;
	private int categoryId;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private String fullDate;
	private String status;
}
