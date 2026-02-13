package com.revastudio.revastudio.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity                     // (JPA) marks this class as a DB-mapped entity
@Table(name = "Employee")   // (JPA) maps to the "Employee" table explicitly
@Getter                     // (Lombok) generates getters
@Setter                     // (Lombok) generates setters
@NoArgsConstructor          // (Lombok) required by JPA
@AllArgsConstructor         // (Lombok) convenience for tests/creation
public class Employee {

    @Id
    @Column(name = "EmployeeId", nullable = false)
    private UUID employeeId;

    @Embedded
    private PII personalIdentifiableInformation;

    @Embedded
    private Address address;

    @Column(name = "Title")
    private String title;

    @Column(name = "ReportsTo")
    private String reportsTo;

    @Column(name = "BirthDate")
    private String birthDate;

    @Column(name = "HireDate")
    private String hireDate;

    /**
     * @OneToMany: One employee supports many customers
     * mappedBy = "supportRep": “The Customer entity owns the relationship via its supportRep field.”
     * This is HUGE: it tells Hibernate where the FK lives and who is responsible for updates.
     * fetch = LAZY: don’t load the whole customer list until asked
     */
    @OneToMany(mappedBy = "supportRep", fetch = FetchType.LAZY)
    /**
     * Collection Initialization
        * Making sure the list is never null (can add to it safely now) & we prevent NullPointerExceptions
     * Can do: employee.getSupportedCustomers().add(customer); // inverse side only
     */
    private List<Customer> supportedCustomer = new ArrayList<>();
}
