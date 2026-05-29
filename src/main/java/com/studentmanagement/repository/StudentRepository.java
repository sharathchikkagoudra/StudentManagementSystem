package com.studentmanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.studentmanagement.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{
	Optional<Student> findByUsn(String usn);
	
	List<Student> findByDepartment(String deparatment);
	
	List<Student> findByNameContainingIgnoreCase(String name);
	
	List<Student> findByEmailContainingIgnoreCase(String email);

	List<Student> findByPhoneNumberContaining(String phoneNumber);

	List<Student> findByDepartmentContainingIgnoreCase(String department);

	List<Student> findByUsnContainingIgnoreCase(String usn);
}
