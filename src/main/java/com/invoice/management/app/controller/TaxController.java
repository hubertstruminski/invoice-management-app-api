package com.invoice.management.app.controller;

import com.invoice.management.app.dto.TaxDto;
import com.invoice.management.app.service.TaxService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/taxes")
public class TaxController {

    private TaxService taxService;

    public TaxController(TaxService taxService) {
        this.taxService = taxService;
    }

    @PostMapping
    public ResponseEntity<TaxDto> createTax(@Valid @RequestBody TaxDto taxDto) {
        return new ResponseEntity<>(taxService.createTax(taxDto), HttpStatus.CREATED);
    }

    @GetMapping
    public List<TaxDto> getAllTaxes() {
        return taxService.getAllTaxes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaxDto> getTaxById(@PathVariable(name = "id") UUID id) {
        return ResponseEntity.ok(taxService.getTaxById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaxDto> updateTax(
            @Valid @RequestBody TaxDto taxDto,
            @PathVariable(name = "id") UUID id) {
        TaxDto taxResponse = taxService.updateTax(taxDto, id);
        return new ResponseEntity<>(taxResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTax(@PathVariable(name = "id") UUID id) {
        taxService.deleteTax(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
