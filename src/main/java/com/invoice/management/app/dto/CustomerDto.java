package com.invoice.management.app.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import java.util.UUID;

@Data
public class CustomerDto {

    private UUID id;

    @NotBlank(message = "Full name field is required")
    private String fullName;

    @NotBlank(message = "Email field is required")
    @Email(message = "Invalid email")
    private String email;

    @Pattern(regexp = "\\[\\+\\d{2}\\]\\s{1}\\d{3}\\s{1}\\d{3}\\s{1}\\d{3}",
            message = "Invalid format! Use [+00] [123] [456] [789] format")
    private String phoneNumber;

    @NotBlank(message = "NIP field is required")
    @Pattern(regexp = "\\d{10}", message = "Invalid format")
    private String nip;

    @NotBlank(message = "Street field is required")
    private String street;

    @NotBlank(message = "City field is required")
    private String city;

    private String country;

    private String description;
}
