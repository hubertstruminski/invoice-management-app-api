package com.invoice.management.app.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class CompanyDto {

    private Long id;

    @NotBlank(message = "Name field is required")
    private String name;

    @NotBlank(message = "Street field is required")
    private String street;

    @NotBlank(message = "Postal code field is required")
    @Pattern(regexp = "\\d{2}-\\d{3}", message = "Invalid postal code! Use 00-000 format")
    private String postalCode;

    @NotBlank(message = "City field is required")
    private String city;

    @NotBlank(message = "Country field is required")
    private String country;
}
