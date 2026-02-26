package com.revastudio.revastudio.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.revastudio.revastudio.dto.CreateCustomerDto;
import com.revastudio.revastudio.dto.CustomerDetailResponse;
import com.revastudio.revastudio.dto.PIIDto;
import com.revastudio.revastudio.dto.mapper.CustomerMapper;
import com.revastudio.revastudio.entity.Customer;
import com.revastudio.revastudio.repo.CustomerRepository;
import com.revastudio.revastudio.util.DTOHelper;
import com.revastudio.revastudio.util.StringUtil;

import lombok.RequiredArgsConstructor;

/**
 * @Service (Spring)
 * This annotation registers this class as a Spring-managed component (aka a "bean"),
 * so Spring will inject it where needed (in your controllers, other services, etc).
 */
@Service
/**
 * @RequiredArgsConstructor (Lombok)
 * This annotation generates a constructor for all final fields.
 * This is the cleanest form of DI because the dependency is immutable and can't be null.
 */
@RequiredArgsConstructor
public class CustomerService {

    /**
     * @private uses encapsulation, meaning only this class can access the repo directly.
     * @final uses immutability, meaning once injected, it cannot be reassigned.
     * Together they ensure thread-safety and prevent accidental modification.
     */
    private final CustomerRepository customerRepo;

    //! CREATE
    public CreateCustomerDto createCustomer(Customer customer) {

        /**
        * @IllegalArgumentException is a runtime exception thrown when a method receives an invalid or inappropriate argument.
        * Used here for customer validation to signal that the caller provided bad data (null, blank, etc).
        * This is an unchecked exception, so callers aren't forced to handle it.
        */
        if (customer == null) { throw new IllegalArgumentException("Customer cannot be null"); }

        // Generate ID in service
        if (customer.getCustomerId() == null) {
            customer.setCustomerId(UUID.randomUUID());
        }

        PIIDto pii = DTOHelper.extractPII(customer.getPersonalIdentifiableInformation());

        if(StringUtil.isBlank(pii.firstName())) { throw new IllegalArgumentException("FirstName is required"); }
        if(StringUtil.isBlank(pii.lastName())) { throw new IllegalArgumentException("LastName is required"); }

        Customer saved = customerRepo.save(customer);

        return CustomerMapper.toCreateResponse(saved);
    }

    //! GET
    public CustomerDetailResponse getCustomerDetail(UUID customerId) {

        if (customerId == null) { throw new IllegalArgumentException("CustomerId cannot be null"); }

        Customer customer = customerRepo.findById(customerId).orElseThrow();

        return CustomerMapper.toDetailResponse(customer);
    }

}
