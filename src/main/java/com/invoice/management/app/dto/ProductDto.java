package com.invoice.management.app.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import java.util.UUID;

@Data
public class ProductDto {

    private UUID id;

    @NotBlank(message = "Name field is required")
    private String name;

    @NotBlank(message = "Price field is required")
    @Pattern(regexp = "\\d+\\.\\d{2}", message = "Invalid format! Use 0.00 format")
    private int price;

    @NotBlank(message = "Amount field is required")
    @Pattern(regexp = "\\d+", message = "Invalid number")
    private int amount;

    private int discount;

    @NotBlank(message = "Unit field is required")
    private String unit;




    private String description;
}
