package com.studentmanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.studentmanagement.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long>{
	
	Optional<Department> findByCode(String code);
	
	boolean existsByNameIgnoreCase(String name);

    boolean existsByCodeIgnoreCase(String code);

}
