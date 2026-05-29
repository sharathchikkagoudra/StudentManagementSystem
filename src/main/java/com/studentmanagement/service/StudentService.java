package com.studentmanagement.service;

import java.util.List;

import com.studentmanagement.dto.StudentCourseRequestDto;
import com.studentmanagement.dto.StudentRequestDto;
import com.studentmanagement.entity.Student;


public interface StudentService {
	
	Student addStudent(StudentRequestDto studentRequestDto);
	
	List<Student> getAllStudents(String sortBy);

    Student getStudentById(Long id);

    Student getStudentByUsn(String usn);

    Student updateStudent(Long id, StudentRequestDto studentRequestDto);

    void deleteStudent(Long id);

    List<Student> getStudentsByDepartment(String department);
    
    List<Student> searchByName(String name);

    List<Student> searchByEmail(String email);

    List<Student> searchByPhone(String phoneNumber);

    List<Student> searchByDepartment(String department);

    List<Student> searchByUsn(String usn);
    
    Student assignCoursesToStudent(StudentCourseRequestDto studentCourseRequestDto);
    
}
