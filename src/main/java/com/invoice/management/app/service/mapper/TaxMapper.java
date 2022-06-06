package com.invoice.management.app.service.mapper;

import com.invoice.management.app.dto.TaxDto;
import com.invoice.management.app.entity.Tax;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface TaxMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mapToDTO(Tax tax, @MappingTarget TaxDto taxDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mapToEntity(TaxDto taxDto, @MappingTarget Tax tax);
}
