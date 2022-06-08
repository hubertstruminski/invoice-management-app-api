package com.invoice.management.app.dto;

import com.invoice.management.app.entity.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class ReadableInvoiceDto {

    private Long id;
    private String number;
    private Date date;
    private Date deadline;
    private String description;
    private boolean sentStatus;
    private Long customerId;
    private List<Product> products;
}
