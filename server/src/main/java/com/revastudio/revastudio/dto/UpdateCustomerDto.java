package com.revastudio.revastudio.dto;

import java.util.UUID;

public record UpdateCustomerDto(String company, UUID supportRepId, AddressDto address, PIIDto pii) {}
