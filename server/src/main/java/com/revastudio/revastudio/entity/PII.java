package com.revastudio.revastudio.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable                     // (JPA) Stored as columns in the owning entity's table
@Getter                         // (Lombok) generates getX() methods so you don’t hand-write them
@Setter                         // (Lombok) generates setX() methods
@NoArgsConstructor              // (Lombok) JPA requires a no-args constructor for entities/embeddables
@AllArgsConstructor             // (Lombok) convenience constructor for quick object creation in tests
public class PII {

    @Column(name = "FirstName")
    private String firstName;

    @Column(name = "LastName")
    private String lastName;

    @Column(name = "Email")
    private String email;

    @Column(name = "Phone")
    private String phone;

    @Column(name = "Fax")
    private String fax;
}
