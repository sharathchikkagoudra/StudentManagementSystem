package com.studentmanagement.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studentmanagement.dto.ApiResponse;
import com.studentmanagement.dto.DepartmentRequestDto;
import com.studentmanagement.entity.Department;
import com.studentmanagement.service.DepartmentService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {
	
	private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @Operation(
    	    summary = "Add a new department",
    	    description = "Creates and saves a new department"
    	)
    	@PostMapping
    	public ApiResponse<Department> addDepartment(
    	        @Valid @RequestBody DepartmentRequestDto departmentRequestDto) {

    	    return new ApiResponse<>(
    	            true,
    	            "Department added succefully",
    	            departmentService.addDepartment(departmentRequestDto)
    	    );
    	}

    	@Operation(
    	    summary = "Get all departments",
    	    description = "Fetches all departments"
    	)
    	@GetMapping
    	public ApiResponse<List<Department>> getAllDepartments() {

    	    return new ApiResponse<>(
    	            true,
    	            "Departments fetched successfully",
    	            departmentService.getAllDepartments()
    	    );
    	}

    	@Operation(
    	    summary = "Update department",
    	    description = "Updates department details using department ID"
    	)
    	@PutMapping("/{departmentId}")
    	public ApiResponse<Department> updateDepartment(
    	        @PathVariable Long departmentId,
    	        @Valid @RequestBody DepartmentRequestDto departmentRequestDto) {

    	    return new ApiResponse<>(
    	            true,
    	            "Department updated successfully",
    	            departmentService.updateDepartment(departmentId, departmentRequestDto)
    	    );
    	}

    	@Operation(
    	    summary = "Delete department",
    	    description = "Deletes a department using department ID"
    	)
    	@DeleteMapping("/{departmentId}")
    	public ApiResponse<Void> deleteDepartment(
    	        @PathVariable Long departmentId) {

    	    departmentService.deleteDepartment(departmentId);

    	    return new ApiResponse<>(
    	            true,
    	            "Department deleted successfully",
    	            null
    	    );
    	}
}
