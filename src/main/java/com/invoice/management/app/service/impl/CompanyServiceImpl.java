package com.invoice.management.app.service.impl;

import com.invoice.management.app.dto.CompanyDto;
import com.invoice.management.app.entity.Company;
import com.invoice.management.app.exception.ResourceNotFoundException;
import com.invoice.management.app.repository.CompanyRepository;
import com.invoice.management.app.service.CompanyService;

import com.invoice.management.app.service.mapper.CompanyMapper;
import com.invoice.management.app.service.mapper.InvoiceMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

//    private final ModelMapper mapper;
    private CompanyMapper mapper;

    private CompanyServiceImpl(CompanyRepository companyRepository, CompanyMapper mapper) {
        this.companyRepository = companyRepository;
        this.mapper = mapper;
    }

    @Override
    public CompanyDto createCompany(CompanyDto companyDto) {
        Company company = new Company();
        mapper.mapToEntity(companyDto, company);

        Company newCompany = companyRepository.save(company);

        CompanyDto newCompanyDto = new CompanyDto();
        mapper.mapToDTO(newCompany, newCompanyDto);

        return newCompanyDto;
    }

    @Override
    public List<CompanyDto> getAllCompanies() {
        List<Company> companies = companyRepository.findAll();
//        this::mapToDTO
        return companies
                .stream()
                .map(company -> {
                    CompanyDto companyDto = new CompanyDto();
                    mapper.mapToDTO(company, companyDto);

                    return companyDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public CompanyDto getCompanyById(Long id) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Company", "id", id.toString()));

        CompanyDto companyDto = new CompanyDto();
        mapper.mapToDTO(company, companyDto);

        return companyDto;
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

        CompanyDto updatedCompanyDto = new CompanyDto();
        mapper.mapToDTO(updatedCompany, updatedCompanyDto);

        return updatedCompanyDto;
    }

    @Override
    public void deleteCompany(Long id) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Company", "id", id.toString()));
        companyRepository.delete(company);
    }

//    private CompanyDto mapToDTO(Company company) {
//        return mapper.map(company, CompanyDto.class);
//    }
//
//    private Company mapToEntity(CompanyDto companyDto) {
//        return mapper.map(companyDto, Company.class);
//    }
}
