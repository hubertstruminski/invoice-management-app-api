package com.invoice.management.app.dto;

import com.invoice.management.app.validation.MinOneElementList;
import com.invoice.management.app.validation.ValidTodayDate;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@Data
public class PersistableInvoiceDto {

    private Long id;

    @NotBlank(message = "Number field is required")
    @Pattern(regexp = "\\d{6}", message = "Invalid number field")
    private String number;

    @ValidTodayDate
    private Date date;

    @Future(message = "Deadline field must be date in future")
    private Date deadline;

    private String description;

    @NotNull(message = "Sent status field is required")
    private boolean sentStatus;

    @NotNull(message = "Customer field is required")
    private Long customerId;

    @NotEmpty(message = "Product list can not be empty")
    @MinOneElementList(message = "Product ids list must have minimum 1 element")
    private List<Long> productIds;
}
