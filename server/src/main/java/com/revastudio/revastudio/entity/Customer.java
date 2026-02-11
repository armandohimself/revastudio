package com.revastudio.revastudio.entity;

import java.util.UUID;
public class Customer {
    private UUID customerId;
    private PII personalIdentifiableInformation;
    private String company;
    private Address address;
    private UUID supportRepId;
}
