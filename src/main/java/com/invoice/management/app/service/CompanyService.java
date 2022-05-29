package com.invoice.management.app.service;

import com.invoice.management.app.dto.CompanyDto;

import java.util.List;

public interface CompanyService {

    CompanyDto createCompany(CompanyDto companyDto);

    List<CompanyDto> getAllCompanies();

    CompanyDto getCompanyById(Long id);

    CompanyDto updateCompany(CompanyDto companyDto, Long id);

    void deleteCompany(Long id);
}
