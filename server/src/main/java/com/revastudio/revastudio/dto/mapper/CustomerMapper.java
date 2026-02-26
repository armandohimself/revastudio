package com.revastudio.revastudio.dto.mapper;

import java.util.UUID;

import com.revastudio.revastudio.dto.AddressDto;
import com.revastudio.revastudio.dto.CreateCustomerDto;
import com.revastudio.revastudio.dto.CustomerDetailResponse;
import com.revastudio.revastudio.dto.PIIDto;
import com.revastudio.revastudio.entity.Customer;
import com.revastudio.revastudio.util.DTOHelper;

public final class CustomerMapper {

    private CustomerMapper() {}

    public static CreateCustomerDto toCreateResponse(Customer customer) {

        PIIDto pii = DTOHelper.extractPII(customer.getPersonalIdentifiableInformation());

        AddressDto address = DTOHelper.extractAddress(customer.getAddress());

        UUID supportRepId = DTOHelper.extractSupportRepId(customer);

        return new CreateCustomerDto(
            customer.getCustomerId(),
            customer.getCompany(),
            pii,
            address,
            supportRepId
        );
    }

    public static CustomerDetailResponse toDetailResponse(Customer customer) {

        PIIDto pii = DTOHelper.extractPII(customer.getPersonalIdentifiableInformation());

        AddressDto address = DTOHelper.extractAddress(customer.getAddress());

        UUID supportRepId = DTOHelper.extractSupportRepId(customer);

        String supportRepName = DTOHelper.extractSupportRepName(customer);

        return new CustomerDetailResponse(
            customer.getCustomerId(),
            customer.getCompany(),
            pii,
            address,
            supportRepId,
            supportRepName
        );
    }
}

