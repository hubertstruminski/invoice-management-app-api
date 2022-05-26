package com.invoice.management.app.service;

import com.invoice.management.app.dto.CompanyDto;
import com.invoice.management.app.dto.TaxDto;

import java.util.List;
import java.util.UUID;

public interface TaxService {

    TaxDto createTax(TaxDto taxDto);

    List<TaxDto> getAllTaxes();

    TaxDto getTaxById(UUID id);

    TaxDto updateTax(TaxDto taxDto, UUID id);

    void deleteTax(UUID id);
}
