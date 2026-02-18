package com.revastudio.revastudio.repo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import com.revastudio.revastudio.entity.Customer;
import com.revastudio.revastudio.entity.Employee;

@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired EmployeeRepository employeeRepo;
    @Autowired CustomerRepository customerRepo;
    @Autowired EntityManager entityManager;

    @Test
    void save_and_findById() {
        // Arrange
        Employee e = new Employee();
        e.setEmployeeId(UUID.randomUUID());
        e.setTitle("Support Rep");

        // Act
        employeeRepo.save(e);
        Employee found = employeeRepo.findById(e.getEmployeeId()).orElseThrow();

        // Assert
        assertEquals(e.getEmployeeId(), found.getEmployeeId());
        assertEquals("Support Rep", found.getTitle());
    }

    @Test
    void findByTitle_returnMatches() {
        // Arrange
        Employee e1 = new Employee();
        e1.setEmployeeId(UUID.randomUUID());
        e1.setTitle("Support Rep");

        Employee e2 = new Employee();
        e2.setEmployeeId(UUID.randomUUID());
        e2.setTitle("CEO");

        // Act
        employeeRepo.saveAll(List.of(e1, e2));

        List<Employee> results = employeeRepo.findByTitle("Support Rep");

        // Assert
        assertEquals(1, results.size());
        assertEquals("Support Rep", results.get(0).getTitle());
    }

    @Test
    void supportRep_customer_relationship_persists_when_both_sides_synced() {

        // Arrange
        Employee e = new Employee();
        e.setEmployeeId(UUID.randomUUID());
        e.setTitle("Support Rep");
        employeeRepo.save(e);

        Customer c = new Customer();
        c.setCustomerId(UUID.randomUUID());
        c.setSupportRep(e);  // Give the Customer 1 Support Rep

        /**
         * Employee can have Many Customers.
         * private List<Customer> supportedCustomer
         * supportedCustomer.add(theCustomer);
         */
        e.getSupportedCustomer().add(c);
        customerRepo.save(c);

        // Act
        Customer foundCustomer = customerRepo.findById(c.getCustomerId()).orElseThrow();

        // Assert
        assertEquals(1, e.getSupportedCustomer().size());
        assertEquals(c.getCustomerId(), e.getSupportedCustomer().get(0).getCustomerId());
        assertEquals(e.getEmployeeId(), foundCustomer.getSupportRep().getEmployeeId());
    }

    @Test
    void supportRep_customer_relationship_persists_after_flush_and_clear() {

        // Arrange
        Employee e = new Employee();
        e.setEmployeeId(UUID.randomUUID());
        e.setTitle("Support Rep");
        employeeRepo.save(e);

        Customer c = new Customer();
        c.setCustomerId(UUID.randomUUID());
        c.setSupportRep(e); // owning side is enough for DB persistence
        customerRepo.save(c);

        // Force SQL write + clear persistence context so we reload from DB
        entityManager.flush();
        entityManager.clear();

        // Act
        Employee found = employeeRepo.findById(e.getEmployeeId()).orElseThrow();

        // Assert
        assertEquals(1, found.getSupportedCustomer().size());
        assertEquals(c.getCustomerId(), found.getSupportedCustomer().get(0).getCustomerId());
    }

}
