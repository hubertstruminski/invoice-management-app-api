package com.invoice.management.app.dto;

import com.invoice.management.app.entity.Product;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.util.List;

@Data
public class TaxDto {

    private Long id;

    @NotBlank(message = "Name field is required")
    private String name;

    @NotNull(message = "Amount field is required")
    private Integer amount;

    private String description;

    private List<Product> products;
}
