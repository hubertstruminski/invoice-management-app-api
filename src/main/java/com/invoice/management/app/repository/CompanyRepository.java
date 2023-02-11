package com.invoice.management.app.repository;

import com.invoice.management.app.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query("SELECT c FROM Company c WHERE c.user.id = :#{#userId}")
    public List<Company> findAll(@Param("userId") Long userId);

    @Query("SELECT c FROM Company c WHERE c.user.id = :#{#userId} AND c.id = :#{#id}")
    public Company findById(@Param("id") Long id, @Param("userId") Long userId);

}
