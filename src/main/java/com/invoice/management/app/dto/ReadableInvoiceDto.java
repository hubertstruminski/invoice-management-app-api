package com.invoice.management.app.dto;

import com.invoice.management.app.entity.Product;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
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
