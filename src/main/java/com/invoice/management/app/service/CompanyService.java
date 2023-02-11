package com.invoice.management.app.service;

import com.invoice.management.app.dto.CompanyDto;

import java.util.List;

public interface CompanyService {

    CompanyDto createCompany(CompanyDto companyDto, Long userId);

    List<CompanyDto> getAllCompanies(Long userId);

    CompanyDto getCompanyById(Long id, Long userId);

    CompanyDto updateCompany(CompanyDto companyDto, Long id);

    void deleteCompany(Long id);
}
