package com.invoice.management.app.service.impl;

import com.invoice.management.app.dto.TaxDto;
import com.invoice.management.app.entity.Tax;
import com.invoice.management.app.exception.ResourceNotFoundException;
import com.invoice.management.app.repository.TaxRepository;
import com.invoice.management.app.service.TaxService;

import com.invoice.management.app.service.mapper.TaxMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaxServiceImpl implements TaxService {

    private final TaxRepository taxRepository;

//    private final ModelMapper mapper;
    private TaxMapper mapper;

    private TaxServiceImpl(TaxRepository taxRepository, TaxMapper mapper) {
        this.taxRepository = taxRepository;
        this.mapper = mapper;
    }

    @Override
    public TaxDto createTax(TaxDto taxDto) {
        Tax tax = new Tax();
        mapper.mapToEntity(taxDto, tax);

        Tax newTax = taxRepository.save(tax);

        TaxDto updatedTaxDto = new TaxDto();
        mapper.mapToDTO(newTax, updatedTaxDto);

        return updatedTaxDto;
    }

    @Override
    public List<TaxDto> getAllTaxes() {
        List<Tax> taxes = taxRepository.findAll();
        return taxes
                .stream()
                .map(tax -> {
                    TaxDto taxDto = new TaxDto();
                    mapper.mapToDTO(tax, taxDto);

                    return taxDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public TaxDto getTaxById(Long id) {
        Tax tax = taxRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tax", "id", id.toString()));

        TaxDto taxDto = new TaxDto();
        mapper.mapToDTO(tax, taxDto);

        return taxDto;
    }

    @Override
    public TaxDto updateTax(TaxDto taxDto, Long id) {
        Tax tax = taxRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tax", "id", id.toString()));

//        tax.setName(taxDto.getName());
//        tax.setAmount(taxDto.getAmount());
//        tax.setDescription(taxDto.getDescription());
        mapper.mapToEntity(taxDto, tax);
        Tax updatedTax = taxRepository.save(tax);

        TaxDto updatedTaxDto = new TaxDto();
        mapper.mapToDTO(updatedTax, updatedTaxDto);

        return updatedTaxDto;
    }

    @Override
    public void deleteTax(Long id) {
        Tax tax = taxRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tax", "id", id.toString()));
        taxRepository.delete(tax);
    }

//    private TaxDto mapToDTO(Tax tax) {
//        return mapper.map(tax, TaxDto.class);
//    }
//
//    private Tax mapToEntity(TaxDto taxDto) {
//        return mapper.map(taxDto, Tax.class);
//    }
}
