package com.studentmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseMarksResponseDto {
	private Long courseId;
	private String courseName;
    private String courseCode;
    private Integer marksObtained;
}
