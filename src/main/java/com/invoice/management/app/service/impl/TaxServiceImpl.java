package com.invoice.management.app.service.impl;

import com.invoice.management.app.dto.TaxDto;
import com.invoice.management.app.entity.Tax;
import com.invoice.management.app.exception.ResourceNotFoundException;
import com.invoice.management.app.repository.TaxRepository;
import com.invoice.management.app.service.TaxService;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaxServiceImpl implements TaxService {

    private final TaxRepository taxRepository;

    private final ModelMapper mapper;

    private TaxServiceImpl(TaxRepository taxRepository, ModelMapper mapper) {
        this.taxRepository = taxRepository;
        this.mapper = mapper;
    }

    @Override
    public TaxDto createTax(TaxDto taxDto) {
        Tax tax = mapToEntity(taxDto);
        Tax newTax = taxRepository.save(tax);

        return mapToDTO(newTax);
    }

    @Override
    public List<TaxDto> getAllTaxes() {
        List<Tax> taxes = taxRepository.findAll();
        return taxes.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public TaxDto getTaxById(Long id) {
        Tax tax = taxRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tax", "id", id.toString()));
        return mapToDTO(tax);
    }

    @Override
    public TaxDto updateTax(TaxDto taxDto, Long id) {
        Tax tax = taxRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tax", "id", id.toString()));

        tax.setName(taxDto.getName());
        tax.setAmount(taxDto.getAmount());
        tax.setDescription(taxDto.getDescription());

        Tax updatedTax = taxRepository.save(tax);
        return mapToDTO(updatedTax);
    }

    @Override
    public void deleteTax(Long id) {
        Tax tax = taxRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tax", "id", id.toString()));
        taxRepository.delete(tax);
    }

    private TaxDto mapToDTO(Tax tax) {
        return mapper.map(tax, TaxDto.class);
    }

    private Tax mapToEntity(TaxDto taxDto) {
        return mapper.map(taxDto, Tax.class);
    }
}
