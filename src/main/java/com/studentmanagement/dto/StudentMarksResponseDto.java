package com.studentmanagement.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentMarksResponseDto {
	private Long studentId;
    private String studentName;
    private String studentUsn;
    private List<CourseMarksResponseDto> marks;
}
