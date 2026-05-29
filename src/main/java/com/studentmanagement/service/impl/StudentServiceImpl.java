package com.studentmanagement.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.studentmanagement.dto.StudentCourseRequestDto;
import com.studentmanagement.dto.StudentRequestDto;
import com.studentmanagement.entity.Course;
import com.studentmanagement.entity.Department;
import com.studentmanagement.entity.Student;
import com.studentmanagement.exception.DepartmentNotFoundException;
import com.studentmanagement.exception.StudentNotFoundException;
import com.studentmanagement.repository.CourseRepository;
import com.studentmanagement.repository.DepartmentRepository;
import com.studentmanagement.repository.StudentRepository;
import com.studentmanagement.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService{
	
	private final StudentRepository studentRepository;
	private final DepartmentRepository departmentRepository;
	private final CourseRepository courseRepository;

    public StudentServiceImpl(StudentRepository studentRepository, DepartmentRepository departmentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.departmentRepository = departmentRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public Student addStudent(StudentRequestDto dto) {
    	Department department = departmentRepository.findById(dto.getDepartmentId())
    			.orElseThrow(() -> new DepartmentNotFoundException("Department not found with id: "+ dto.getDepartmentId()));

        Student student = new Student();

        student.setName(dto.getName());
        student.setUsn(dto.getUsn());
        student.setDateOfBirth(dto.getDateOfBirth());
        student.setPhoneNumber(dto.getPhoneNumber());
        student.setEmail(dto.getEmail());
        student.setDepartment(department);

        return studentRepository.save(student);
    }

    @Override
    public List<Student> getAllStudents(String sortBy) {
        return studentRepository.findAll(Sort.by(sortBy));
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));
    }

    @Override
    public Student getStudentByUsn(String usn) {
        return studentRepository.findByUsn(usn)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with usn: " + usn));
    }

    @Override
    public Student updateStudent(Long id, StudentRequestDto dto) {
    	Department department = departmentRepository.findById(dto.getDepartmentId())
    			.orElseThrow(() -> new DepartmentNotFoundException("Department not found with id: "+ dto.getDepartmentId()));

        Student existingStudent = getStudentById(id);

        existingStudent.setName(dto.getName());
        existingStudent.setUsn(dto.getUsn());
        existingStudent.setDateOfBirth(dto.getDateOfBirth());
        existingStudent.setPhoneNumber(dto.getPhoneNumber());
        existingStudent.setEmail(dto.getEmail());
        existingStudent.setDepartment(department);

        return studentRepository.save(existingStudent);
    }

    @Override
    public void deleteStudent(Long id) {
        Student student = getStudentById(id);
        studentRepository.delete(student);
    }

    @Override
    public List<Student> getStudentsByDepartment(String department) {
        return studentRepository.findByDepartment(department);
    }
    
    @Override
    public List<Student> searchByName(String name) {
        return studentRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public List<Student> searchByEmail(String email) {
        return studentRepository.findByEmailContainingIgnoreCase(email);
    }

    @Override
    public List<Student> searchByPhone(String phoneNumber) {
        return studentRepository.findByPhoneNumberContaining(phoneNumber);
    }

    @Override
    public List<Student> searchByDepartment(String department) {
        return studentRepository.findByDepartmentContainingIgnoreCase(department);
    }

    @Override
    public List<Student> searchByUsn(String usn) {
        return studentRepository.findByUsnContainingIgnoreCase(usn);
    }

    @Override
    public Student assignCoursesToStudent(
            StudentCourseRequestDto studentCourseRequestDto) {

        Student student = studentRepository
                .findById(studentCourseRequestDto.getStudentId())

                .orElseThrow(() ->
                        new StudentNotFoundException(
                                "Student not found with id: "
                                        + studentCourseRequestDto.getStudentId()
                        )
                );

        List<Course> newCourses =
                courseRepository.findAllById(
                        studentCourseRequestDto.getCourseIds()
                );

        if (newCourses.size()
                != studentCourseRequestDto.getCourseIds().size()) {

            throw new RuntimeException(
                    "One or more course IDs are invalid"
            );
        }

        List<Course> existingCourses =
                student.getCourses();

        for (Course newCourse : newCourses) {

            boolean alreadyAssigned = existingCourses.stream()

                    .anyMatch(existingCourse ->
                            existingCourse.getId()
                                    .equals(newCourse.getId())
                    );

            if (alreadyAssigned) {

                throw new RuntimeException(
                        "Course already assigned to student: "
                                + newCourse.getCourseName()
                );
            }

            existingCourses.add(newCourse);
        }

        student.setCourses(existingCourses);

        return studentRepository.save(student);
    }
	
}
