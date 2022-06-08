package com.invoice.management.app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToMany()
    @JoinTable(
            name = "invoices_products",
            joinColumns = @JoinColumn(name = "invoice_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> products;

    public void removeProduct(Product product) {
        products.remove(product);
        product.getInvoices().remove(this);
    }
}
