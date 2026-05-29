package com.studentmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PassPercentageResponseDto {
	private int totalStudents;
    private int passedStudents;
    private int failedStudents;
    private double passPercentage;
}
