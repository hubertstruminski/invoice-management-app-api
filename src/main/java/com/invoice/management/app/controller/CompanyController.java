package com.invoice.management.app.controller;

import com.invoice.management.app.dto.CompanyDto;
import com.invoice.management.app.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping
    public ResponseEntity<CompanyDto> createCompany(@Valid @RequestBody CompanyDto companyDto) {
        return new ResponseEntity<>(companyService.createCompany(companyDto), HttpStatus.CREATED);
    }

    @GetMapping
    public List<CompanyDto> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDto> getCompanyById(@PathVariable(name = "id") UUID id) {
        return ResponseEntity.ok(companyService.getCompanyById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyDto> updateCompany(
            @Valid @RequestBody CompanyDto companyDto,
            @PathVariable(name = "id") UUID id) {
        CompanyDto companyResponse = companyService.updateCompany(companyDto, id);
        return new ResponseEntity<>(companyResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable(name = "id") UUID id) {
        companyService.deleteCompany(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
