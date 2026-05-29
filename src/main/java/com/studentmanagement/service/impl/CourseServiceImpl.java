package com.studentmanagement.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.studentmanagement.dto.CourseRequestDto;
import com.studentmanagement.entity.Course;
import com.studentmanagement.entity.Student;
import com.studentmanagement.repository.CourseRepository;
import com.studentmanagement.repository.MarksRepository;
import com.studentmanagement.service.CourseService;

@Service
public class CourseServiceImpl implements CourseService{

    private final MarksRepository marksRepository;
	private final CourseRepository courseRepository;
	
	public CourseServiceImpl(CourseRepository courseRepository, MarksRepository marksRepository) {
		this.courseRepository = courseRepository;
		this.marksRepository = marksRepository;
	}

	@Override
	public Course addCourse(CourseRequestDto courseRequestDto) {
		if(courseRepository.existsByCourseCodeIgnoreCase(courseRequestDto.getCourseCode())) {
			throw new RuntimeException("Course code already exists");
		}
		
		Course course = new Course();
		course.setCourseName(courseRequestDto.getCourseName());
		course.setCourseCode(courseRequestDto.getCourseCode());
		course.setCredits(courseRequestDto.getCredits());
		
		return courseRepository.save(course);
	}

	@Override
	public List<Course> getAllCourses() {
		return courseRepository.findAll();
	}

	@Override
	public void deleteCourse(String courseCode) {

	    Course course = courseRepository.findByCourseCodeIgnoreCase(courseCode)
	            .orElseThrow(() -> new RuntimeException("Course not found with code: "+ " " + courseCode));

	    for (Student student : course.getStudents()) {
	        student.getCourses().remove(course);
	    }

	    courseRepository.delete(course);
	}
	
	@Override
	public Course updateCourse(Long courseId, CourseRequestDto courseRequestDto) {

	    Course course = courseRepository.findById(courseId)
	            .orElseThrow(() -> new RuntimeException("Course not found with id: " + " " + courseId));

	    course.setCourseName(courseRequestDto.getCourseName());
	    course.setCourseCode(courseRequestDto.getCourseCode());
	    course.setCredits(courseRequestDto.getCredits());

	    return courseRepository.save(course);
	}

}
