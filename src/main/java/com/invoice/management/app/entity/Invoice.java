package com.invoice.management.app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    @Type(type = "uuid-char")
    private UUID id;

    @Column(name = "number", nullable = false)
    private String number;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "deadline", nullable = false)
    private Date deadline;

    @Column(name = "description")
    private String description;

    @Column(name = "sent_status", nullable = false)
    private boolean sentStatus;
}
