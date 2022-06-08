package com.invoice.management.app.service.mapper;

import com.invoice.management.app.dto.CustomerDto;
import com.invoice.management.app.entity.Customer;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CustomerDto mapToDTO(Customer customer, @MappingTarget CustomerDto customerDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Customer mapToEntity(CustomerDto customerDto, @MappingTarget Customer customer);
}
