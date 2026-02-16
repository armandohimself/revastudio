package com.revastudio.revastudio.dto;

import java.util.UUID;

public record CustomerListItemResponse(UUID customerId, String company, UUID supportRepId, String supportRepName) {}
