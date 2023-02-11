package com.invoice.management.app.service.impl;

import com.invoice.management.app.dto.CompanyDto;
import com.invoice.management.app.entity.Company;
import com.invoice.management.app.entity.User;
import com.invoice.management.app.exception.ResourceNotFoundException;
import com.invoice.management.app.repository.CompanyRepository;
import com.invoice.management.app.repository.UserRepository;
import com.invoice.management.app.service.CompanyService;
import com.invoice.management.app.service.mapper.CompanyMapper;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final CompanyMapper mapper;

    private CompanyServiceImpl(CompanyRepository companyRepository, CompanyMapper mapper, UserRepository userRepository) {
        this.companyRepository = companyRepository;
        this.mapper = mapper;
        this.userRepository = userRepository;
    }

    @Override
    public CompanyDto createCompany(CompanyDto companyDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId.toString()));

        Company company = mapper.mapToEntity(companyDto, new Company());
        company.setUser(user);

        Company newCompany = companyRepository.save(company);

        return mapper.mapToDTO(newCompany, new CompanyDto());
    }

    @Override
    public List<CompanyDto> getAllCompanies(Long userId) {
        List<Company> companies = companyRepository.findAll(userId);

        return companies
                .stream()
                .map(company -> mapper.mapToDTO(company, new CompanyDto()))
                .collect(Collectors.toList());
    }

    @Override
    public CompanyDto getCompanyById(Long id, Long userId) {
        Company company = companyRepository.findById(id, userId);
        if(company == null) {
            throw new ResourceNotFoundException("Company", "id", id.toString());
        }
        return mapper.mapToDTO(company, new CompanyDto());
    }

    @Override
    public CompanyDto updateCompany(CompanyDto companyDto, Long id, Long userId) {
        Company company = companyRepository.findById(id, userId);
        if(company == null) {
            throw new ResourceNotFoundException("Company", "id", id.toString());
        }

        mapper.mapToEntity(companyDto, company);
        Company updatedCompany = companyRepository.save(company);

        return mapper.mapToDTO(updatedCompany, new CompanyDto());
    }

    @Override
    public void deleteCompany(Long id, Long userId) {
        Company company = companyRepository.findById(id, userId);
        if(company == null) {
            throw new ResourceNotFoundException("Company", "id", id.toString());
        }
        companyRepository.delete(company);
    }
}
