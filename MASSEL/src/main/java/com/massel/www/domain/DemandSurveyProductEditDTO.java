package com.massel.www.domain;

import java.util.List;

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
public class DemandSurveyProductEditDTO {

	private List<DemandSurveyProductVO> productList;
	private int dno;
}
