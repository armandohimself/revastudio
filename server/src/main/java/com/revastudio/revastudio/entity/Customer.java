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

@Entity
@Table(name = "Customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @Column(name = "CustomerId", nullable = false)
    private UUID customerId;

    @Embedded
    private PII personalIdentifiableInformation;

    @Embedded
    private Address address;

    @Column(name = "Company")
    private String company;

    @Column(name = "SupportRepId")
    private UUID supportRepId;
}
