package com.revastudio.revastudio.dto;

import java.util.UUID;

public record CustomerDetailResponse(
    UUID customerId,
    String company,
    UUID supportRepId,
    String supportRepName,
    AddressDto address,
    PIIDto pii
) {}
