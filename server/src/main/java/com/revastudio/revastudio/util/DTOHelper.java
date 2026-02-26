package com.revastudio.revastudio.util;

import java.util.UUID;

import com.revastudio.revastudio.dto.AddressDto;
import com.revastudio.revastudio.dto.PIIDto;
import com.revastudio.revastudio.entity.Address;
import com.revastudio.revastudio.entity.Customer;
import com.revastudio.revastudio.entity.PII;

public class DTOHelper {

    public static UUID extractSupportRepId(Customer customer) {

        /**
         * @IllegalArgumentException is a runtime exception thrown when a method receives an invalid or inappropriate argument.
         * Used here, we are validating PII to signal that the caller provided bad data (null, blank, etc).
         * This is an unchecked exception, so callers aren't forced to handle it unlike try-catch.
         */
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }

        if (customer.getSupportRep() == null) {
            return null;
        }

        return customer.getSupportRep().getEmployeeId();
    }

    public static String extractSupportRepName(Customer customer) {

        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }

        if (customer.getSupportRep() == null) {
            return null;
        }

        String supportRepName =
            (customer.getSupportRep().getPersonalIdentifiableInformation().getFirstName()
            + " " +
            customer.getSupportRep().getPersonalIdentifiableInformation().getLastName())
            .trim();

        return supportRepName;
    }

    public static PIIDto extractPII(PII customerPII) {

        if (customerPII == null) {
            throw new IllegalArgumentException("PII cannot be null");
        }

        return new PIIDto(
            customerPII.getFirstName(),
            customerPII.getLastName(),
            customerPII.getEmail(),
            customerPII.getPhone(),
            customerPII.getFax()
        );
    }

    public static AddressDto extractAddress(Address customerAddress) {

        if(customerAddress == null) {
            throw new IllegalArgumentException("Address cannot be null");
        }

        return new AddressDto(
            customerAddress.getAddress(),
            customerAddress.getCity(),
            customerAddress.getState(),
            customerAddress.getCountry(),
            customerAddress.getPostalCode()
        );
    }
}
