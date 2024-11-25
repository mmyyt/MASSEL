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
public class DateValueVO {
    private String startDay;
    private String startHour;
    private String startMinute;
    private String endDay;
    private String endHour;
    private String endMinute;
}
