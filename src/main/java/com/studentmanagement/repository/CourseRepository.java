package com.studentmanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.studentmanagement.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>{
	
	boolean existsByCourseCodeIgnoreCase(String courseCode);
	
	Optional<Course> findByCourseCodeIgnoreCase(String courseCode);
}
