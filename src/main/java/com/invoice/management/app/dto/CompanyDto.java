package com.invoice.management.app.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CompanyDto {
    private UUID id;
    private String name;
    private String street;
    private String postalCode;
    private String city;
    private String country;
}
