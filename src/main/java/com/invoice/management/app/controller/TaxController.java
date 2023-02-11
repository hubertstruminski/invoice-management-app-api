package com.invoice.management.app.controller;

import com.invoice.management.app.dto.ReadableProductDto;
import com.invoice.management.app.dto.TaxDto;
import com.invoice.management.app.security.JwtTokenProvider;
import com.invoice.management.app.service.TaxService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/taxes")
public class TaxController {

    private final TaxService taxService;
    private final JwtTokenProvider tokenProvider;

    public TaxController(TaxService taxService, JwtTokenProvider tokenProvider) {
        this.taxService = taxService;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping
    public ResponseEntity<TaxDto> createTax(@Valid @RequestBody TaxDto taxDto,
                                            @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        Long userId = tokenProvider.getUserIdFromJWT(token.split(" ")[1]);
        return new ResponseEntity<>(taxService.createTax(taxDto, userId), HttpStatus.CREATED);
    }

    @GetMapping
    public List<TaxDto> getAllTaxes(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        Long userId = tokenProvider.getUserIdFromJWT(token.split(" ")[1]);
        return taxService.getAllTaxes(userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaxDto> getTaxById(@PathVariable(name = "id") Long id,
                                             @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        Long userId = tokenProvider.getUserIdFromJWT(token.split(" ")[1]);
        return ResponseEntity.ok(taxService.getTaxById(id, userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaxDto> updateTax(
            @Valid @RequestBody TaxDto taxDto,
            @PathVariable(name = "id") Long id) {
        TaxDto taxResponse = taxService.updateTax(taxDto, id);
        return new ResponseEntity<>(taxResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTax(@PathVariable(name = "id") Long id) {
        taxService.deleteTax(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
