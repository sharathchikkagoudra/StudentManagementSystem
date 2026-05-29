package com.studentmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RankResponseDto {
	private Integer rank;
    private String studentName;
    private String studentUsn;
    private Double percentage;
    private String finalResult;
}
