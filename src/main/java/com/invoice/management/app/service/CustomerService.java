package com.invoice.management.app.service;

import com.invoice.management.app.dto.CompanyDto;
import com.invoice.management.app.dto.CustomerDto;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    CustomerDto createCustomer(CustomerDto customerDto);

    List<CustomerDto> getAllCustomers();

    CustomerDto getCustomerById(UUID id);

    CustomerDto updateCustomer(CustomerDto customerDto, UUID id);

    void deleteCustomer(UUID id);
}
