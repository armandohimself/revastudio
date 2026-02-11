package com.revastudio.revastudio.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
}
