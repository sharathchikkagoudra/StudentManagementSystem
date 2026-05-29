package com.studentmanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.studentmanagement.entity.Marks;

@Repository
public interface MarksRepository extends JpaRepository<Marks, Long>{
	List<Marks> findByStudentId(Long studentId);
	
	boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);
	
	Optional<Marks> findByStudentIdAndCourseId(Long studentId, Long courseId);
}
