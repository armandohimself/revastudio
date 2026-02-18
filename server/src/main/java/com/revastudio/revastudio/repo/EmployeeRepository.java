package com.revastudio.revastudio.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revastudio.revastudio.entity.Employee;

/**
 * JpaRepository (Spring Data JPA)
 * Solves: gives you CRUD methods for free: save(), findById(), deleteById(), etc.
 * No implementation class needed; Spring generates it at runtime.
 */
public interface EmployeeRepository extends JpaRepository<Employee, UUID>{
    
    List<Employee> findByTitle(String title);
}

/**
 * ? Q:
 * - interface
 * - extends
 */
