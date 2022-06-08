package com.invoice.management.app.service.impl;

import com.invoice.management.app.dto.TaxDto;
import com.invoice.management.app.entity.Invoice;
import com.invoice.management.app.entity.Product;
import com.invoice.management.app.entity.Tax;
import com.invoice.management.app.exception.ResourceNotFoundException;
import com.invoice.management.app.repository.TaxRepository;
import com.invoice.management.app.service.TaxService;
import com.invoice.management.app.service.mapper.TaxMapper;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaxServiceImpl implements TaxService {

    private final TaxRepository taxRepository;
    private final TaxMapper mapper;

    public TaxServiceImpl(TaxRepository taxRepository, TaxMapper mapper) {
        this.taxRepository = taxRepository;
        this.mapper = mapper;
    }

    @Override
    public TaxDto createTax(TaxDto taxDto) {
        Tax tax = mapper.mapToEntity(taxDto, new Tax());
        Tax newTax = taxRepository.save(tax);

        return mapper.mapToDTO(newTax, new TaxDto());
    }

    @Override
    public List<TaxDto> getAllTaxes() {
        List<Tax> taxes = taxRepository.findAll();
        return taxes
                .stream()
                .map(tax -> mapper.mapToDTO(tax, new TaxDto()))
                .collect(Collectors.toList());
    }

    @Override
    public TaxDto getTaxById(Long id) {
        Tax tax = taxRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tax", "id", id.toString()));

        return mapper.mapToDTO(tax, new TaxDto());
    }

    @Override
    public TaxDto updateTax(TaxDto taxDto, Long id) {
        Tax tax = taxRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tax", "id", id.toString()));

        mapper.mapToEntity(taxDto, tax);
        Tax updatedTax = taxRepository.save(tax);

        return mapper.mapToDTO(updatedTax, new TaxDto());
    }

    @Override
    @Transactional
    public void deleteTax(Long id) {
        Tax tax = taxRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tax", "id", id.toString()));
        List<Product> products = tax.getProducts();

        for(Product product: products) {
            for(Invoice invoice: product.getInvoices()) {
                invoice.removeProduct(product);
            }
        }

        taxRepository.delete(tax);
    }
}
