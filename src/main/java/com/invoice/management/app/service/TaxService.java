package com.invoice.management.app.service;

import com.invoice.management.app.dto.TaxDto;

import java.util.List;

public interface TaxService {

    TaxDto createTax(TaxDto taxDto);

    List<TaxDto> getAllTaxes();

    TaxDto getTaxById(Long id);

    TaxDto updateTax(TaxDto taxDto, Long id);

    void deleteTax(Long id);
}
