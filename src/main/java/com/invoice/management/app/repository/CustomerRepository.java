package com.invoice.management.app.repository;

import com.invoice.management.app.entity.Company;
import com.invoice.management.app.entity.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c WHERE c.user.id = :#{#userId}")
    public List<Customer> findAll(@Param("userId") Long userId);

    @Query("SELECT c FROM Customer c WHERE c.user.id = :#{#userId} AND c.id = :#{#id}")
    public Customer findById(@Param("id") Long id, @Param("userId") Long userId);
}
