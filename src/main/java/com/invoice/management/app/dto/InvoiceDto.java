package com.invoice.management.app.dto;

import com.invoice.management.app.validation.ValidTodayDate;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.Date;

@Data
public class InvoiceDto {

    private Long id;

    @NotBlank(message = "Number field is required")
    @Pattern(regexp = "\\d{6}", message = "Invalid number")
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
}
