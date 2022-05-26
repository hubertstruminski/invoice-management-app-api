package com.invoice.management.app.dto;

import com.invoice.management.app.validation.ValidTodayDate;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.UUID;

@Data
public class InvoiceDto {

    private UUID id;

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
}
