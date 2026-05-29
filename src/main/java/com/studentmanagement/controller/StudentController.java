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
import com.studentmanagement.dto.StudentCourseRequestDto;
import com.studentmanagement.dto.StudentRequestDto;
import com.studentmanagement.entity.Student;
import com.studentmanagement.service.StudentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/students")
@Tag(
	name = "Student Management APIs",
	description = "APIs for managing student operations"
)
public class StudentController {
	private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @Operation(
    	summary = "Add a new Student",
    	description = "Creates and saves new student"
    )
    @PostMapping
    public ApiResponse<Student> addStudent(@Valid @RequestBody StudentRequestDto studentRequestDto) {
        return new ApiResponse<>(
        		true,
        		"Student added successfully",
        		studentService.addStudent(studentRequestDto)
        );
    }
    
    @Operation(
    	summary = "Get all students",
    	description = "Fetch all students with optional sorting"
    )
    @GetMapping
    public ApiResponse<List<Student>> getAllStudents(@RequestParam(defaultValue = "id") String sortBy) {
        return new ApiResponse<>(
                true,
                "Students fetched successfully",
                studentService.getAllStudents(sortBy)
        );
    }

    @Operation(
    	summary = "Get student by ID",
    	description = "Fetch a student using student ID"
    )
    @GetMapping("/{id}")
    public ApiResponse<Student> getStudentById(@PathVariable Long id) {
        return new ApiResponse<>(
                true,
                "Student fetched successfully",
                studentService.getStudentById(id)
        );
    }

    @Operation(
    	summary = "Get student by USN",
    	description = "Fetch a student using USN"
    )
    @GetMapping("/usn/{usn}")
    public ApiResponse<Student> getStudentByUsn(@PathVariable String usn) {
        return new ApiResponse<>(
                true,
                "Student fetched successfully",
                studentService.getStudentByUsn(usn)
        );
    }

    @Operation(
    	summary = "Update student",
    	description = "Update existing student details"
    )
    @PutMapping("/{id}")
    public ApiResponse<Student> updateStudent(@PathVariable Long id,
                                              @Valid @RequestBody StudentRequestDto studentRequestDto) {
        return new ApiResponse<>(
                true,
                "Student updated successfully",
                studentService.updateStudent(id, studentRequestDto)
        );
    }

    @Operation(
    	summary = "Delete student",
    	description = "Delete a student using ID"
    )
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return new ApiResponse<>(
                true,
                "Student deleted successfully",
                null
        );
    }

    @Operation(
    	summary = "Get student by department",
    	description = "Fetch students belonging to a department"
    )
    @GetMapping("/department/{department}")
    public ApiResponse<List<Student>> getStudentsByDepartment(@PathVariable String department) {
        return new ApiResponse<>(
                true,
                "Students fetched successfully",
                studentService.getStudentsByDepartment(department)
        );
    }

    @Operation(
    	summary = "Search students by name",
    	description = "Search students using name"
    )
    @GetMapping("/searchByName")
    public ApiResponse<List<Student>> searchByName(@RequestParam String name) {
        return new ApiResponse<>(
                true,
                "Students fetched successfully",
                studentService.searchByName(name)
        );
    }

    @Operation(
    	summary = "Search students by email",
    	description = "Search students using email"
    )
    @GetMapping("/searchByEmail")
    public ApiResponse<List<Student>> searchByEmail(@RequestParam String email) {
        return new ApiResponse<>(
                true,
                "Students fetched successfully",
                studentService.searchByEmail(email)
        );
    }

    @Operation(
    	summary = "Search students by phone number",
    	description = "Search students using phone number"
    )
    @GetMapping("/searchByPhone")
    public ApiResponse<List<Student>> searchByPhone(@RequestParam String phoneNumber) {
        return new ApiResponse<>(
                true,
                "Students fetched successfully",
                studentService.searchByPhone(phoneNumber)
        );
    }

    @Operation(
    	summary = "Search students by department",
    	description = "Search students using department"
    )
    @GetMapping("/searchByDepartment")
    public ApiResponse<List<Student>> searchByDepartment(@RequestParam String department) {
        return new ApiResponse<>(
                true,
                "Students fetched successfully",
                studentService.searchByDepartment(department)
        );
    }

    @Operation(
    	summary = "Search students by USN",
    	description = "Search students using USN"
    )
    @GetMapping("/searchByUsn")
    public ApiResponse<List<Student>> searchByUsn(@RequestParam String usn) {
        return new ApiResponse<>(
                true,
                "Students fetched successfully",
                studentService.searchByUsn(usn)
        );
    }

    @Operation(
    	summary = "Assign courses to student",
    	description = "Assign multiple courses to a student"
    )
    @PutMapping("/assign-courses")
    public ApiResponse<Student> assignCoursesToStudent(@Valid @RequestBody StudentCourseRequestDto studentCourseRequestDto) {
        return new ApiResponse<>(
                true,
                "Courses assigned to student successfully",
                studentService.assignCoursesToStudent(studentCourseRequestDto)
        );
    }
}
