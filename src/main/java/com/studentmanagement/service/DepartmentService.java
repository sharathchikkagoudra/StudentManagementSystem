package com.studentmanagement.service;

import java.util.List;

import com.studentmanagement.dto.DepartmentRequestDto;
import com.studentmanagement.entity.Department;

public interface DepartmentService {
	Department addDepartment(DepartmentRequestDto departmentRequestDto);

    List<Department> getAllDepartments();
    
    Department updateDepartment(Long departmentId, DepartmentRequestDto departmentRequestDto);

    void deleteDepartment(Long departmentId);
}
