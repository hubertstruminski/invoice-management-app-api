package com.invoice.management.app.service;

import com.invoice.management.app.dto.TaxDto;

import java.util.List;

public interface TaxService {

    TaxDto createTax(TaxDto taxDto, Long userId);

    List<TaxDto> getAllTaxes(Long userId);

    TaxDto getTaxById(Long id, Long userId);

    TaxDto updateTax(TaxDto taxDto, Long id);

    void deleteTax(Long id, Long userId);
}
