package com.invoice.management.app.service.mapper;

import com.invoice.management.app.dto.ProductDto;
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

    @Mapping(source = "product.tax", target = "productDto.taxId", qualifiedByName = "taxToId")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract ProductDto mapToDTO(Product product, @MappingTarget ProductDto productDto);

    @Mapping(source = "productDto.taxId", target = "product.tax", qualifiedByName = "idToTax")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract Product mapToEntity(ProductDto productDto, @MappingTarget Product product);

    @Named("idToTax")
    public Tax mapIdToTax(Long id) {
        return taxRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tax", "id", id.toString()));
    }

    @Named("taxToId")
    public Long mapTaxToId(Tax tax) {
        return tax.getId();
    }
}
