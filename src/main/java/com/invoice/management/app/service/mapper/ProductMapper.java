package com.invoice.management.app.service.mapper;

import com.invoice.management.app.dto.PersistableInvoiceDto;
import com.invoice.management.app.dto.PersistableProductDto;
import com.invoice.management.app.dto.ReadableProductDto;
import com.invoice.management.app.entity.Product;
import com.invoice.management.app.entity.Tax;
import com.invoice.management.app.exception.ResourceNotFoundException;

import com.invoice.management.app.repository.TaxRepository;
import org.mapstruct.*;

import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class ProductMapper {

    @Autowired
    protected TaxRepository taxRepository;

//    @Mapping(source = "product.tax", target = "productDto.taxId", qualifiedByName = "taxToId")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract ReadableProductDto mapToDTO(Product product, @MappingTarget ReadableProductDto readableProductDto);

    @Mapping(source = "productDto.taxId", target = "product.tax", qualifiedByName = "idToTax")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract Product mapToEntity(PersistableProductDto productDto, @MappingTarget Product product);

    @Named("idToTax")
    public Tax mapIdToTax(Long id) {
        return taxRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tax", "id", id.toString()));
    }

//    @Named("taxToId")
//    public Long mapTaxToId(Tax tax) {
//        return tax.getId();
//    }
}
