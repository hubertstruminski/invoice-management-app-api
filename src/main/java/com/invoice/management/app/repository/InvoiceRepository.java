package com.invoice.management.app.repository;

import com.invoice.management.app.entity.Customer;
import com.invoice.management.app.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    @Query("SELECT i FROM Invoice i WHERE i.user.id = :#{#userId}")
    public List<Invoice> findAll(@Param("userId") Long userId);

    @Query("SELECT i FROM Invoice i WHERE i.user.id = :#{#userId} AND i.id = :#{#id}")
    public Invoice findById(@Param("id") Long id, @Param("userId") Long userId);
}
