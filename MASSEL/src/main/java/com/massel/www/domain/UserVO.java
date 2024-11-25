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
public class UserVO {

	private String id;
	private String pw;
	private String email;
	private String nickname;
	private String isSeller;  //판매자로 등록되어있는지 여부, default F
	private String regDate; 
	private String role;
	
	
}
