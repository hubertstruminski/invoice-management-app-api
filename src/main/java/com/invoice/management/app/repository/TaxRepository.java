package com.invoice.management.app.repository;

import com.invoice.management.app.entity.Product;
import com.invoice.management.app.entity.Tax;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaxRepository extends JpaRepository<Tax, Long> {

    @Query("SELECT t FROM Tax t WHERE t.user.id = :#{#userId}")
    public List<Tax> findAll(@Param(("userId")) Long userId);

    @Query("SELECT t FROM Tax t WHERE t.user.id = :#{#userId} AND t.id = :#{#id}")
    public Tax findById(@Param("id") Long id, @Param("userId") Long userId);
}
