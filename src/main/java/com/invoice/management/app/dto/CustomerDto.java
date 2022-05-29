package com.invoice.management.app.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class CustomerDto {

    private Long id;

    @NotBlank(message = "Full name field is required")
    private String fullName;

    @NotBlank(message = "Email field is required")
    @Email(regexp = ".+[@].+[\\.].+", message = "Invalid email")
    private String email;

    @Pattern(regexp = "[^A-Za-z]+",
            message = "Invalid phone number! You can not use letters")
    private String phoneNumber;

    @NotBlank(message = "NIP field is required")
    @Pattern(regexp = "\\d{10}", message = "NIP field has invalid format")
    private String nip;

    @NotBlank(message = "Street field is required")
    private String street;

    @NotBlank(message = "City field is required")
    private String city;

    private String country;

    private String description;
}
