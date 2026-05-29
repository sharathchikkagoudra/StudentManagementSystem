package com.studentmanagement.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.studentmanagement.dto.DepartmentRequestDto;
import com.studentmanagement.entity.Department;
import com.studentmanagement.entity.Student;
import com.studentmanagement.repository.DepartmentRepository;
import com.studentmanagement.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService{
	private final DepartmentRepository departmentRepository;
	
	public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
		this.departmentRepository = departmentRepository;
	}
	
	public Department addDepartment(DepartmentRequestDto dto) {
		Department department = new Department();
		
		if (departmentRepository.existsByNameIgnoreCase(dto.getName())) {
            throw new RuntimeException("Department name already exists");
        }

        if (departmentRepository.existsByCodeIgnoreCase(dto.getCode())) {
            throw new RuntimeException("Department code already exists");
        }
		
		department.setName(dto.getName());
		department.setCode(dto.getCode());
		return departmentRepository.save(department);
	}
	
	public List<Department> getAllDepartments(){
		return departmentRepository.findAll();
	}
	
	@Override
	public Department updateDepartment(Long departmentId, DepartmentRequestDto departmentRequestDto) {

	    Department department = departmentRepository.findById(departmentId)
	            .orElseThrow(() -> new RuntimeException("Department not found with id: " + departmentId));

	    department.setName(departmentRequestDto.getName());
	    department.setCode(departmentRequestDto.getCode());

	    return departmentRepository.save(department);
	}

	@Override
	public void deleteDepartment(Long departmentId) {

	    Department department = departmentRepository.findById(departmentId)
	            .orElseThrow(() -> new RuntimeException("Department not found with id: " + departmentId));

	    for (Student student : department.getStudents()) {
	        student.setDepartment(null);
	    }

	    departmentRepository.delete(department);
	}

}
