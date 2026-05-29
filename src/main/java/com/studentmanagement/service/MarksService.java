package com.studentmanagement.service;

import java.util.List;

import com.studentmanagement.dto.FailedStudentResponseDto;
import com.studentmanagement.dto.MarksRequestDto;
import com.studentmanagement.dto.PassPercentageResponseDto;
import com.studentmanagement.dto.RankResponseDto;
import com.studentmanagement.dto.ResultResponseDto;
import com.studentmanagement.dto.StudentMarksResponseDto;
import com.studentmanagement.entity.Marks;

public interface MarksService {
	
	Marks addMarks(MarksRequestDto marksRequestDto);
	
	List<StudentMarksResponseDto> getAllMarks();
	
	StudentMarksResponseDto getMarksByStudentId(Long studentId);
	
	Marks updateMarks(MarksRequestDto marksRequestDto);
	
	void deleteMarks(Long studentId, Long courseId);
	
	ResultResponseDto getStudentResult(Long studentId);
	
	List<RankResponseDto> getRankList();
	
	List<RankResponseDto> getTopperStudents();
	
	List<FailedStudentResponseDto> getFailedStudents();
	
	PassPercentageResponseDto getPassPercentage();
	
	List<FailedStudentResponseDto> searchFailedStudentsByUsn(String usn);
	
	List<ResultResponseDto> getAllStudentResults();
	
	List<ResultResponseDto> searchResultsByUsn(String usn);
	
	List<RankResponseDto> searchRankListByUsn(String usn);

}
