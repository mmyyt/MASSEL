package com.massel.www.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class DemandSurveyDataVO {

	private DemandSurveyVO dsvo;
	private DemandSurveyImgVO dsivo;
	private List<DemandSurveyProductVO> dspvo;
	private int participationCount;
}
