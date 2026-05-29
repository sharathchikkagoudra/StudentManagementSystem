package com.studentmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SubjectResultDto {
	private String courseName;
    private String courseCode;
    private Integer marksObtained;
    private String grade;
    private String result;
}
