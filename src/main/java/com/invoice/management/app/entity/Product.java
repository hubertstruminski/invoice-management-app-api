package com.invoice.management.app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "amount", nullable = false)
    private int amount;

    @Column(name = "discount")
    private int discount;

    @Column(name = "unit", nullable = false)
    private String unit;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tax_id", nullable = false)
    private Tax tax;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "invoice_id")
//    private Invoice invoice;
}
