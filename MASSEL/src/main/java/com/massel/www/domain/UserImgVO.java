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
public class UserImgVO {
	
	private String uuid;
	private String saveDir;
	private String fileName;
	private long fileSize;
	private int fileType;
	private String id;
}
