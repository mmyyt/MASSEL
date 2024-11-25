package com.massel.www.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DemandSurveyParticipantDTO {
	
	private DemandSurveyVO dsvo;
	private DemandSurveyImgVO dsivo;
	private int participantCount;

}
