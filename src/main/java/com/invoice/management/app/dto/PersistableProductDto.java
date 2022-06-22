package com.invoice.management.app.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class PersistableProductDto {

    private Long id;

    @NotBlank(message = "Name field is required")
    private String name;

    @NotNull(message = "Price field is required")
    private Integer price;

    @NotNull(message = "Amount field is required")
    private Integer amount;

    private Integer discount;

    @NotBlank(message = "Unit field is required")
    private String unit;

    @NotNull(message = "Tax field is required")
    private Long taxId;
//    private Tax tax;

    private String description;
}
