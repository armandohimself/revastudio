package com.revastudio.revastudio.dto;

import java.util.UUID;

record CreateCustomerDto(String company, UUID supportRepId, AddressDto address, PIIDto pii) {}
