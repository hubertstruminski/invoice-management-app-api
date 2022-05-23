package com.invoice.management.app.service.impl;

import com.invoice.management.app.dto.CompanyDto;
import com.invoice.management.app.entity.Company;
import com.invoice.management.app.repository.CompanyRepository;
import com.invoice.management.app.service.CompanyService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

    private CompanyRepository companyRepository;

    private ModelMapper mapper;

    private CompanyServiceImpl(CompanyRepository companyRepository, ModelMapper mapper) {
        this.companyRepository = companyRepository;
        this.mapper = mapper;
    }

    @Override
    public CompanyDto createCompany(CompanyDto companyDto) {
        Company company = mapToEntity(companyDto);
        Company newCompany = companyRepository.save(company);

        CompanyDto companyResponse = mapToDTO(newCompany);
        return companyResponse;
    }

    @Override
    public List<CompanyDto> getAllCompanies() {
        List<Company> companies = companyRepository.findAll();
        return companies.stream().map(company -> mapToDTO(company)).collect(Collectors.toList());
    }

    private CompanyDto mapToDTO(Company company) {
        return mapper.map(company, CompanyDto.class);
    }

    private Company mapToEntity(CompanyDto companyDto) {
        return mapper.map(companyDto, Company.class);
    }
}
