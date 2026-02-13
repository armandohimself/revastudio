package com.revastudio.revastudio.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revastudio.revastudio.entity.Address;
import com.revastudio.revastudio.entity.Employee;
import com.revastudio.revastudio.entity.PII;
import com.revastudio.revastudio.repo.EmployeeRepository;

/**
 * @ExtendWith(MockitoExtension.class) (Mockito + JUnit)
 * Solves: turns on Mockito annotations like @Mock and @InjectMocks for this test class.
 * Without it, @Mock fields stay null.
 */
@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    /**
     * @Mock (Mockito)
     * Solves: creates a fake EmployeeRepository so we don’t need a real database.
     */
    @Mock
    private EmployeeRepository employeeRepo;

    @InjectMocks
    private EmployeeService employeeService;

    /**
     * @Test (JUnit)
     * Solves: marks this method as a runnable test.
     */
    @Test
    void create_generateEmployeeId_whenMissing_andSaves() {
        // Arrange
        Employee newEmployee = new Employee(
            null, // missing id
            new PII("Armando", "Arteaga", "hey@armandohimself.com", "(773) 365-1010", "(313) 828-8765"),
            new Address("3963 W Belmont", "Chicago", "IL", "USA", "60618"),
            "Support Rep",
            null,
            "1993-01-19",
            "2026-02-11",
            null
        );

        // When repo.save is called, just return the object passed in
        when(employeeRepo.save(any(Employee.class))).thenAnswer(inv -> inv.getArgument(0));

        // Act
        Employee createdEmployee = employeeService.create(newEmployee);

        // Assert
        assertNotNull(createdEmployee.getEmployeeId(), "EmployeeId should be generated");

        verify(employeeRepo, times(1)).save(any(Employee.class));
    }

    @Test
    void create_throws_whenPIIMissing() {
        Employee createdEmployee = new Employee(
            null,
            null,
            null,
            "Support Rep",
            null,
            null,
            null,
            null
        );

        assertThrows(IllegalArgumentException.class, () -> employeeService.create(createdEmployee));
        verify(employeeRepo, never()).save(any()); // should not save invalid data
    }

}

/**
 * ? Q:
 * - no public class?
 * - when(employeeRepo.save(any(Employee.class))).thenAnswer(inv -> inv.getArguments());
 * - also ...inv.getArguments(0)...
 */
