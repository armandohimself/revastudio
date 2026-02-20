package com.revastudio.revastudio.dto;

import java.util.UUID;

public record CustomerDetailResponse(
    UUID customerId,
    String company,
    PIIDto pii,
    AddressDto address,
    UUID supportRepId,
    String supportRepName
) {}
