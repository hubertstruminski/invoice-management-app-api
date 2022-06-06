package com.invoice.management.app.service.mapper;

import com.invoice.management.app.dto.CompanyDto;
import com.invoice.management.app.entity.Company;

import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mapToDTO(Company company, @MappingTarget CompanyDto companyDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mapToEntity(CompanyDto companyDto, @MappingTarget Company company);
}
