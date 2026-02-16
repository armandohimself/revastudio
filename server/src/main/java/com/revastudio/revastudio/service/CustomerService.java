package com.revastudio.revastudio.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.revastudio.revastudio.entity.Customer;
import com.revastudio.revastudio.entity.PII;
import com.revastudio.revastudio.repo.CustomerRepository;
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

    private final CustomerRepository customerRepo;

    public Customer create(Customer input) {

        if (input == null) { throw new IllegalArgumentException("CustomerId is required"); }

        // Generate ID in service
        if (input.getCustomerId() == null) {
            input.setCustomerId(UUID.randomUUID());
        }

        PII pii = input.getPersonalIdentifiableInformation();
        if (pii == null) { throw new IllegalArgumentException("PII is required"); }

        if(StringUtil.isBlank(pii.getFirstName())) { throw new IllegalArgumentException("FirstName is required"); }
        if(StringUtil.isBlank(pii.getLastName())) { throw new IllegalArgumentException("LastName is required"); }

        return customerRepo.save(input);
    }

}
