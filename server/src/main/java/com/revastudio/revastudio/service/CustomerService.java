package com.revastudio.revastudio.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.revastudio.revastudio.entity.Customer;
import com.revastudio.revastudio.entity.PII;
import com.revastudio.revastudio.repo.CustomerRepository;
import com.revastudio.revastudio.util.StringUtil;

import lombok.RequiredArgsConstructor;

@Service
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
