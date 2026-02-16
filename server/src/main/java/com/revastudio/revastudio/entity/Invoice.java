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

/**
 * An invoice is a formal document issued by a seller to a buyer, requesting payment for goods or services provided.
 * It serves as a detailed record of a transaction.
 * Outlining the items or services delivered, their quantities, unit prices, total amounts, applicable taxes, and payment terms.
 * Such as the due date and accepted payment methods.
 */
@Entity
@Table(name = "Invoice")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {

    @Id
    @Column(name = "InvoiceId")
    private UUID invoiceId;

    @Column(name = "CustomerId")
    private UUID customerId;

    @Embedded
    private Address billingAddress;

    @Column(name = "Total")
    private Number total;
}
