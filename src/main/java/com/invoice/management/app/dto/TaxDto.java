package com.invoice.management.app.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import java.util.UUID;

@Data
public class TaxDto {

    private UUID id;

    @NotBlank(message = "Name field is required")
    private String name;

    @NotBlank(message = "Amount field is required")
    @Pattern(regexp = "\\d+", message = "Invalid number")
    private int amount;

    private String description;
}
