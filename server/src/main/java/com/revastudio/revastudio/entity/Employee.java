package com.revastudio.revastudio.entity;

import java.util.UUID;

public class Employee {
    private UUID employeeId;
    private PII personalIdentifiableInformation;
    private String title;
    private String reportsTo;
    private String birthDate;
    private String hireDate;
    private Address address;
}
