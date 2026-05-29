package com.studentmanagement.service;

import java.util.List;

import com.studentmanagement.dto.CourseRequestDto;
import com.studentmanagement.entity.Course;

public interface CourseService {
	
	Course addCourse(CourseRequestDto course);
	
	List<Course> getAllCourses();
	
	Course updateCourse(Long courseId, CourseRequestDto courseRequestDto);
	
	void deleteCourse(String couseCode);
}
