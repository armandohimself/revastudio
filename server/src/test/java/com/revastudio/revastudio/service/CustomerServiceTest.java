package com.revastudio.revastudio.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revastudio.revastudio.entity.Address;
import com.revastudio.revastudio.entity.Customer;
import com.revastudio.revastudio.entity.PII;
import com.revastudio.revastudio.repo.CustomerRepository;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTets {

    @Mock
    CustomerRepository customerRepo;

    @InjectMocks
    CustomerService customerService;

    @Test
    void create_generateCustomerId_whenMissing_AndSaves() {

        // Arrange
        Customer newCustomer = new Customer(
            null,
            new PII("Mando", "Teaga", "armandoarteaga@gmail.com", "(313) 828-8765", "(313) 624-6482"),
            new Address("7086 W Lafayette", "Detroit", "MI", "USA", "48209"),
            "randomCompany",
            null
        );

        when(customerRepo.save(any(Customer.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Customer createdCustomer = customerService.create(newCustomer);

        // Assert
        assertNotNull(createdCustomer.getCustomerId(), "CustomerId should be generated");
        verify(customerRepo, times(1)).save(any(Customer.class));
    }

    @Test
    void create_throws_whenPIIMissing() {
        Customer createdCustomer = new Customer(null, null, null, null, null);

        assertThrows(IllegalArgumentException.class, () -> customerService.create(createdCustomer));
        verify(customerRepo, never()).save(any()); // should not save invalid data
    }
}
