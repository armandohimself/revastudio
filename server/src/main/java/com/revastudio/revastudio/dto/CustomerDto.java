package com.revastudio.revastudio.dto;

import java.util.UUID;

public record CustomerDto() {
    record CreateCustomerDto(String company, UUID supportRepId, AddressDto address, PIIDto pii) {}
    record UpdateCustomerDto(String company, UUID supportRepId, AddressDto address) {}
}
