package com.invoice.management.app.controller;

import com.invoice.management.app.dto.CompanyDto;
import com.invoice.management.app.repository.UserRepository;
import com.invoice.management.app.security.JwtTokenProvider;
import com.invoice.management.app.service.CompanyService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;
    private final JwtTokenProvider tokenProvider;
    private final UserRepository userRepository;

    public CompanyController(CompanyService companyService, JwtTokenProvider tokenProvider, UserRepository userRepository) {
        this.companyService = companyService;
        this.tokenProvider = tokenProvider;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<CompanyDto> createCompany(@Valid @RequestBody CompanyDto companyDto,
                                                    @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        Long userId = tokenProvider.getUserIdFromJWT(token.split(" ")[1]);
        return new ResponseEntity<>(companyService.createCompany(companyDto, userId), HttpStatus.CREATED);
    }

    @GetMapping
    public List<CompanyDto> getAllCompanies(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        Long userId = tokenProvider.getUserIdFromJWT(token.split(" ")[1]);
        return companyService.getAllCompanies(userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDto> getCompanyById(@PathVariable(name = "id") Long id,
                                                     @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        Long userId = tokenProvider.getUserIdFromJWT(token.split(" ")[1]);
        return ResponseEntity.ok(companyService.getCompanyById(id, userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyDto> updateCompany(
            @Valid @RequestBody CompanyDto companyDto,
            @PathVariable(name = "id") Long id,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        Long userId = tokenProvider.getUserIdFromJWT(token.split(" ")[1]);

        CompanyDto companyResponse = companyService.updateCompany(companyDto, id, userId);
        return new ResponseEntity<>(companyResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable(name = "id") Long id,
                                              @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        Long userId = tokenProvider.getUserIdFromJWT(token.split(" ")[1]);
        companyService.deleteCompany(id, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
