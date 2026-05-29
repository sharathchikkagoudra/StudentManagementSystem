package com.studentmanagement.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.studentmanagement.dto.ApiResponse;
import com.studentmanagement.dto.FailedStudentResponseDto;
import com.studentmanagement.dto.MarksRequestDto;
import com.studentmanagement.dto.PassPercentageResponseDto;
import com.studentmanagement.dto.RankResponseDto;
import com.studentmanagement.dto.ResultResponseDto;
import com.studentmanagement.dto.StudentMarksResponseDto;
import com.studentmanagement.entity.Marks;
import com.studentmanagement.service.MarksService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/marks")
public class MarksController {
	private final MarksService marksService;
	
	public MarksController(MarksService marksService) {
		this.marksService = marksService;
	}
	
	@Operation(
		    summary = "Add marks",
		    description = "Creates and saves marks for a student and course"
		)
		@PostMapping
		public ApiResponse<Marks> addMarks(
		        @Valid @RequestBody MarksRequestDto marksRequestDto) {

		    return new ApiResponse<>(
		            true,
		            "Marks added successfully",
		            marksService.addMarks(marksRequestDto)
		    );
		}

		@Operation(
		    summary = "Get all marks",
		    description = "Fetches all student marks"
		)
		@GetMapping
		public ApiResponse<List<StudentMarksResponseDto>> getAllMarks() {
		    return new ApiResponse<>(
		            true,
		            "Marks fetched successfully",
		            marksService.getAllMarks()
		    );
		}

		@Operation(
		    summary = "Get marks by student ID",
		    description = "Fetches all marks of a particular student using student ID"
		)
		@GetMapping("/student/{studentId}")
		public ApiResponse<StudentMarksResponseDto> getMarksByStudentId(
		        @PathVariable Long studentId) {

		    return new ApiResponse<>(
		            true,
		            "Student marks fetched successfully",
		            marksService.getMarksByStudentId(studentId)
		    );
		}

		@Operation(
		    summary = "Update marks",
		    description = "Updates marks of a student for a course"
		)
		@PutMapping
		public ApiResponse<Marks> updateMarks(
		        @Valid @RequestBody MarksRequestDto marksRequestDto) {

		    return new ApiResponse<>(
		            true,
		            "Marks updated successfully",
		            marksService.updateMarks(marksRequestDto)
		    );
		}

		@Operation(
		    summary = "Delete marks",
		    description = "Deletes marks using student ID and course ID"
		)
		@DeleteMapping("/student/{studentId}/course/{courseId}")
		public ApiResponse<Void> deleteMarks(
		        @PathVariable Long studentId,
		        @PathVariable Long courseId) {

		    marksService.deleteMarks(studentId, courseId);

		    return new ApiResponse<>(
		            true,
		            "Marks deleted successfully",
		            null
		    );
		}
		
		@Operation(
		        summary = "Get Student Result",
		        description = "Fetches subject-wise result, grades, percentage, and final result for a specific student"
		)
		@GetMapping("/result/{studentId}")
		public ApiResponse<ResultResponseDto> getStudentResult(@PathVariable Long studentId) {

		    return new ApiResponse<>(
		            true,
		            "Result fetched successfully",
		            marksService.getStudentResult(studentId)
		    );
		}
		
		@Operation(
		        summary = "Get Rank List",
		        description = "Fetches students ranked by percentage in descending order"
		)
		@GetMapping("/rank-list")
		public ApiResponse<List<RankResponseDto>> getRankList() {

		    return new ApiResponse<>(
		            true,
		            "Rank list fetched successfully",
		            marksService.getRankList()
		    );
		}
		
		@Operation(
		        summary = "Get Topper Students",
		        description = "Fetches all students holding rank 1"
		)
		@GetMapping("/topper")
		public ApiResponse<List<RankResponseDto>> getTopperStudents() {

		    return new ApiResponse<>(
		            true,
		            "Topper students fetched successfully",
		            marksService.getTopperStudents()
		    );
		}
		
		@Operation(
		        summary = "Get Failed Students",
		        description = "Fetches all students whose final result is FAIL"
		)
		@GetMapping("/failed-students")
		public ApiResponse<List<FailedStudentResponseDto>> getFailedStudents() {

		    return new ApiResponse<>(
		            true,
		            "Failed students fetched successfully",
		            marksService.getFailedStudents()
		    );
		}
		
		@Operation(
		        summary = "Get Pass Percentage",
		        description = "Fetches pass/fail statistics and pass percentage"
		)
		@GetMapping("/pass-percentage")
		public ApiResponse<PassPercentageResponseDto> getPassPercentage() {

		    return new ApiResponse<>(
		            true,
		            "Pass percentage fetched successfully",
		            marksService.getPassPercentage()
		    );
		}
		
		@Operation(
		        summary = "Get all Student Result",
		        description = "Fetches all students subject-wise result, grades, percentage, and final result"
		)
		@GetMapping("/results")
		public ApiResponse<List<ResultResponseDto>>
		getAllStudentResults() {

		    return new ApiResponse<>(

		            true,

		            "All student results fetched successfully",

		            marksService.getAllStudentResults()
		    );
		}
		
		@Operation(
			    summary = "Search Student Results By USN",
			    description = "This API searches student results using full or partial USN "
			    		+ "and returns subject-wise marks, grades, percentage, and final result details."
		)
		@GetMapping("/results/searchByUsn")
		public ApiResponse<List<ResultResponseDto>> searchResultsByUsn(@RequestParam String usn) {

		    return new ApiResponse<>(

		            true,

		            "Results fetched successfully",

		            marksService.searchResultsByUsn(usn)
		    );
		}
		
		@Operation(
			    summary = "Search Rank List By USN",
			    description = "Searches student ranks using full or partial USN."			
		)

		@GetMapping("/rank-list/searchByUsn")

		public ApiResponse<List<RankResponseDto>> searchRankListByUsn(@RequestParam String usn) {

			return new ApiResponse<>(

			    true,

			    "Rank list fetched successfully",

			    marksService.searchRankListByUsn(usn)
			);
		}
		
		@Operation(
			    summary = "Search Failed Students By USN",
			    description = "Searches failed students using full or partial USN."
		)

		@GetMapping("/failed-students/searchByUsn")
		public ApiResponse<List<FailedStudentResponseDto>> searchFailedStudentsByUsn(@RequestParam String usn) {

			return new ApiResponse<>(

			     true,

			     "Failed students fetched successfully",

			      marksService.searchFailedStudentsByUsn(usn)
			);
		}

}
