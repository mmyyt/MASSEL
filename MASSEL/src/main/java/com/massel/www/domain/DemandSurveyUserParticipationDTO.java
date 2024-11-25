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
public class DemandSurveyUserParticipationDTO {

	//유저의 수요조사 참여 내역 리스트를 가져오기 위해 만든 dTO
	
	private DemandSurveyVO dsvo;
	private DemandSurveyImgVO dsivo;
	private DemandSurveyParticipationVO dsppvo;
	
}
