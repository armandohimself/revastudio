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
public class Address {
    @Column(name = "Address")   // (JPA) explicit mapping from field -> DB column name so JPA doesn't guess names
    private String address;

    @Column(name = "City")
    private String city;

    @Column(name = "State")
    private String state;

    @Column(name = "Country")
    private String country;

    @Column(name = "PostalCode")
    private String postalCode;
}

