package com.studentmanagement.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studentmanagement.dto.ApiResponse;
import com.studentmanagement.dto.CourseRequestDto;
import com.studentmanagement.entity.Course;
import com.studentmanagement.service.impl.CourseServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
	private final CourseServiceImpl courseService;
	
	public CourseController(CourseServiceImpl courseService) {
		this.courseService = courseService;
	}
	
	@Operation(
		    summary = "Add a new course",
		    description = "Creates and saves a new course"
		)
		@PostMapping
		public ApiResponse<Course> addCourse(
		        @Valid @RequestBody CourseRequestDto courseRequestDto) {

		    return new ApiResponse<>(
		            true,
		            "Course updated successfully",
		            courseService.addCourse(courseRequestDto)
		    );
		}

		@Operation(
		    summary = "Get all courses",
		    description = "Fetches all available courses"
		)
		@GetMapping
		public ApiResponse<List<Course>> getAllCorses() {

		    return new ApiResponse<>(
		            true,
		            "Course fetched successfully",
		            courseService.getAllCourses()
		    );
		}

		@Operation(
		    summary = "Update course",
		    description = "Updates course details using course ID"
		)
		@PutMapping("/{courseId}")
		public ApiResponse<Course> updateCourse(
		        @PathVariable Long courseId,
		        @Valid @RequestBody CourseRequestDto courseRequestDto) {

		    return new ApiResponse<>(
		            true,
		            "Course updated successfully",
		            courseService.updateCourse(courseId, courseRequestDto)
		    );
		}

		@Operation(
		    summary = "Delete course",
		    description = "Deletes a course using course code"
		)
		@DeleteMapping("/delete/{courseCode}")
		public ApiResponse<Void> deleteCourse(
		        @PathVariable String courseCode) {

		    courseService.deleteCourse(courseCode);

		    return new ApiResponse<>(
		            true,
		            "Course with course code: " + courseCode + " deleted successfully",
		            null
		    );
		}
}
