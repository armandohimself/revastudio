package com.revastudio.revastudio.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    /**
     * @ManyToOne: Many Customer rows relate to one Employee row.
     * fetch = LAZY (lazy loading): Load the Employee object later when you access it.
        * This is called Hibernate Proxy meaning supportRep gets a placeholder until you call it.
        * If called outside the DB transaction, like if DB closed, then you get a LazyInitializationException¸
        * Usher is backstage for now.
     * optional = true: “This relationship can be null” (FK column can be nullable)
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    /**
     * This field maps using the Customer table column SupportRepId, which points at the Employee table column EmployeeId.
        * Customer.SupportRepId -> Employee.EmployeeId
     * referencedColumnName = "EmployeeId": the target column in the Employee table
     * If EmployeeId is the PK, you can often omit referencedColumnName because PK is the default.
     */
    @JoinColumn(name = "SupportRepId", referencedColumnName = "EmployeeId")
    /**
     * association (object relationship): By calling Employee and not UUID, we can do this --> customer.getSupportRep().getTitle()
     * Our Usher will stand next to the customer unless we call lazy loading, then Usher waits backstage curtains until called.
     * Can do: customer.setSupportRep(employee); // this updates SupportRepId
     */
    private Employee supportRep;
}
