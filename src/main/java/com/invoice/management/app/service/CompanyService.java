package com.invoice.management.app.service;

import com.invoice.management.app.dto.CompanyDto;

import java.util.List;
import java.util.UUID;

public interface CompanyService {

    CompanyDto createCompany(CompanyDto companyDto);

    List<CompanyDto> getAllCompanies();

    CompanyDto getCompanyById(UUID id);

    CompanyDto updateCompany(CompanyDto companyDto, UUID id);

    void deleteCompany(UUID id);
}
