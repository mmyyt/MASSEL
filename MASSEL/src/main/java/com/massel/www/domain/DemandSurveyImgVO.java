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
public class DemandSurveyImgVO {

	private int dno;
	private String uuid;
	private String saveDir;
	private String fileName;
	private int fileType;
	private long fileSize;
	private String regDate;
	
}
