package com.invoice.management.app.service.mapper;

import com.invoice.management.app.dto.CompanyDto;
import com.invoice.management.app.entity.Company;

import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CompanyDto mapToDTO(Company company, @MappingTarget CompanyDto companyDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Company mapToEntity(CompanyDto companyDto, @MappingTarget Company company);
}
