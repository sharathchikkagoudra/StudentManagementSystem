package com.studentmanagement.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentCourseRequestDto {
	@NotNull(message = "Student ID is required")
    private Long studentId;

    @NotEmpty(message = "At least one course ID is required")
    private List<Long> courseIds;
}
