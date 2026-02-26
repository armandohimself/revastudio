package com.revastudio.revastudio.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record CreateCustomerDto(
    UUID customerId,
    String company,
    PIIDto pii,
    AddressDto address,
    // @NotNull(message = "supportRepId is required")
    UUID supportRepId
) {}
