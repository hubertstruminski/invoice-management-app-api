package com.invoice.management.app.repository;

import com.invoice.management.app.entity.Invoice;
import com.invoice.management.app.entity.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.user.id = :#{#userId}")
    public List<Product> findAll(@Param(("userId")) Long userId);

    @Query("SELECT p FROM Product p WHERE p.user.id = :#{#userId} AND p.id = :#{#id}")
    public Product findById(@Param("id") Long id, @Param("userId") Long userId);
}
