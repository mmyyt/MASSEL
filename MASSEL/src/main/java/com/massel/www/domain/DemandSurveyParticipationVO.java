package com.massel.www.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class DemandSurveyParticipationVO {

	private int dno;
	private int dpno;
	private String id;
	private String dpname;
	private int dpprice;
	private int count;
	private String participationDate;
	private String email;
	private String nickname;
}
