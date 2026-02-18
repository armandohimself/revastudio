package com.revastudio.revastudio.repo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import com.revastudio.revastudio.entity.Customer;
import com.revastudio.revastudio.entity.Employee;

@DataJpaTest
class CustomerRepositoryTest {

    /**
    * @Autowired - Tells Spring to inject (wire) a bean dependency automatically.
    * It lets Spring resolve and provide the required object at runtime.
    * Commonly used on constructors, fields, or setters (constructor injection is preferred).
    */
    @Autowired CustomerRepository customerRepo;
    @Autowired EmployeeRepository employeeRepo;

    @Test
    void save_and_findById() {
        // Arrange
        Customer c = new Customer();
        c.setCustomerId(UUID.randomUUID());
        c.setCompany("RevaStudio");

        // Act
        customerRepo.save(c);

        /**
         * findById().orElseThrow() is needed because:
         * findById returns an Optional which is a container that MAY or MAY NOT have the value.
         * --> Optional<Customer> is what's being returned.
         */
        Customer found = customerRepo.findById(c.getCustomerId()).orElseThrow();

        // Assert
        
        /**
         * JUnit
         * assertEquals(actual, expected)
         *
         * AssertJ (richer, fluent style API)
         * assertThat(actual).isEqualTo(expected)
         */
        assertEquals("RevaStudio", found.getCompany());
    }

    @Test
    void findByCompany_returnMatches() {
        Customer c1 = new Customer();
        c1.setCustomerId(UUID.randomUUID());
        c1.setCompany("RevaStudio");

        Customer c2 = new Customer();
        c2.setCustomerId(UUID.randomUUID());
        c2.setCompany("Other LLC");

        customerRepo.saveAll(List.of(c1, c2));

        List<Customer> results = customerRepo.findByCompany("RevaStudio");

        /**
         * JUnit
         * assertEquals(1, results.size());
         * assertTrue(results.size() == 1); // (alternative approach)
         * assertEquals("RevaStudio", results.get(0).getCompany());
         *
         * AssertJ
         * assertThat(results).hasSize(1);
         * assertThat(results.get(0).getCompany()).isEqualTo("Reva Studio");
         */
        assertEquals(1, results.size());
        assertEquals("RevaStudio", results.get(0).getCompany());
    }

    @Test
    void customer_supportRep_relationship_persists() {
        // Arrange: create and save an employee
        Employee e = new Employee();
        e.setEmployeeId(UUID.randomUUID());
        e.setTitle("Support Rep");
        employeeRepo.save(e);

        // Create customer linked to that employee
        Customer c = new Customer();
        c.setCustomerId(UUID.randomUUID());
        c.setSupportRep(e);                         // <-- this sets the FK via @ManyToOne/@JoinColumn
        customerRepo.save(c);

        // Act: reload customer back in
        Customer found = customerRepo.findById(c.getCustomerId()).orElseThrow();

        // Assert: FK relationship resolves
        assertNotNull(found.getSupportRep());
        assertEquals(e.getEmployeeId(), found.getSupportRep().getEmployeeId());
        assertEquals("Support Rep", e.getTitle());

    }

}
