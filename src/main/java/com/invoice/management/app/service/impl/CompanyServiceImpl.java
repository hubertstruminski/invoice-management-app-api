package com.invoice.management.app.service.impl;

import com.invoice.management.app.dto.CompanyDto;
import com.invoice.management.app.entity.Company;
import com.invoice.management.app.exception.ResourceNotFoundException;
import com.invoice.management.app.repository.CompanyRepository;
import com.invoice.management.app.service.CompanyService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
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

    @Override
    public CompanyDto getCompanyById(UUID id) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Company", "id", id.toString()));
        return mapToDTO(company);
    }

    @Override
    public CompanyDto updateCompany(CompanyDto companyDto, UUID id) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Company", "id", id.toString()));

        company.setName(companyDto.getName());
        company.setStreet(companyDto.getStreet());
        company.setPostalCode(companyDto.getPostalCode());
        company.setCity(companyDto.getCity());
        company.setCountry(companyDto.getCountry());

        Company updatedCompany = companyRepository.save(company);
        return mapToDTO(updatedCompany);
    }

    @Override
    public void deleteCompany(UUID id) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Company", "id", id.toString()));
        companyRepository.delete(company);
    }

    private CompanyDto mapToDTO(Company company) {
        return mapper.map(company, CompanyDto.class);
    }

    private Company mapToEntity(CompanyDto companyDto) {
        return mapper.map(companyDto, Company.class);
    }
}
