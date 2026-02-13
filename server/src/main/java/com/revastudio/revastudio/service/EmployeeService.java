package com.revastudio.revastudio.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.revastudio.revastudio.entity.Employee;
import com.revastudio.revastudio.entity.PII;
import com.revastudio.revastudio.repo.EmployeeRepository;
import com.revastudio.revastudio.util.StringUtil;

import lombok.RequiredArgsConstructor;

/**
 * @Service (Spring)
 * Solves: registers this class as a Spring-managed component (a "bean"),
 * so Spring can inject it where needed (controllers, other services).
 * (In pure unit tests, we don’t need Spring to run, but it prepares you for real app wiring.)
 */
@Service
/**
 * @RequiredArgsConstructor (Lombok)
 * Solves: generates a constructor for all final fields.
 * This is the cleanest form of dependency injection (DI = Dependency Injection).
 */
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepo; // injected via generated constructor

    public Employee create(Employee input) {
        if (input == null) { throw new IllegalArgumentException("Employee is required"); }

        // Generate ID in service
        if (input.getEmployeeId() == null) {
            input.setEmployeeId(UUID.randomUUID());
        }

        PII pii = input.getPersonalIdentifiableInformation();
        if (pii == null) throw new IllegalArgumentException("PII is required");
        if (StringUtil.isBlank(pii.getFirstName())) throw new IllegalArgumentException("First name is required");
        if (StringUtil.isBlank(pii.getLastName())) throw new IllegalArgumentException("Last name is required");

        // Save (repo is mocked in unit test, real in production)
        return employeeRepo.save(input);
    }
}
