package com.invoice.management.app.service.impl;

import com.invoice.management.app.dto.CompanyDto;
import com.invoice.management.app.entity.Company;
import com.invoice.management.app.exception.ResourceNotFoundException;
import com.invoice.management.app.repository.CompanyRepository;
import com.invoice.management.app.service.CompanyService;
import com.invoice.management.app.service.mapper.CompanyMapper;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper mapper;

    private CompanyServiceImpl(CompanyRepository companyRepository, CompanyMapper mapper) {
        this.companyRepository = companyRepository;
        this.mapper = mapper;
    }

    @Override
    public CompanyDto createCompany(CompanyDto companyDto) {
        Company company = mapper.mapToEntity(companyDto, new Company());

        Company newCompany = companyRepository.save(company);

        return mapper.mapToDTO(newCompany, new CompanyDto());
    }

    @Override
    public List<CompanyDto> getAllCompanies() {
        List<Company> companies = companyRepository.findAll();
        return companies
                .stream()
                .map(company -> mapper.mapToDTO(company, new CompanyDto()))
                .collect(Collectors.toList());
    }

    @Override
    public CompanyDto getCompanyById(Long id) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Company", "id", id.toString()));
        return mapper.mapToDTO(company, new CompanyDto());
    }

    @Override
    public CompanyDto updateCompany(CompanyDto companyDto, Long id) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Company", "id", id.toString()));

//        company.setName(companyDto.getName());
//        company.setStreet(companyDto.getStreet());
//        company.setPostalCode(companyDto.getPostalCode());
//        company.setCity(companyDto.getCity());
//        company.setCountry(companyDto.getCountry());

        mapper.mapToEntity(companyDto, company);
        Company updatedCompany = companyRepository.save(company);

        return mapper.mapToDTO(updatedCompany, new CompanyDto());
    }

    @Override
    public void deleteCompany(Long id) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Company", "id", id.toString()));
        companyRepository.delete(company);
    }
}
