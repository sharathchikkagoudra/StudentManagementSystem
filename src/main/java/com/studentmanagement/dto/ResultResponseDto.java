package com.studentmanagement.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResultResponseDto {
	private Long studentId;
    private String studentName;
    private String studentUsn;

    private List<SubjectResultDto> subjects;

    private Integer totalMarks;
    private Double percentage;

    private String finalResult;
}
