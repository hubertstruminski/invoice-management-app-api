package com.invoice.management.app.repository;

import com.invoice.management.app.entity.Tax;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxRepository extends JpaRepository<Tax, Long> {
}
