package com.invoice.management.app.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ProductDto {

    private Long id;

    @NotBlank(message = "Name field is required")
    private String name;

    @NotNull(message = "Price field is required")
//    @Pattern(regexp = "\\d+\\.\\d{2}", message = "Invalid price! Use 0.00 format")
    private int price;

    @NotNull(message = "Amount field is required")
//    @Pattern(regexp = "\\d+", message = "Invalid amount. Use number")
    private int amount;

    private int discount;

    @NotBlank(message = "Unit field is required")
    private String unit;

    @NotNull(message = "Tax field is required")
    private Long taxId;

    private String description;
}
