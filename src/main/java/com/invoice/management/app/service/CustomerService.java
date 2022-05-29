package com.invoice.management.app.service;

import com.invoice.management.app.dto.CustomerDto;

import java.util.List;

public interface CustomerService {

    CustomerDto createCustomer(CustomerDto customerDto);

    List<CustomerDto> getAllCustomers();

    CustomerDto getCustomerById(Long id);

    CustomerDto updateCustomer(CustomerDto customerDto, Long id);

    void deleteCustomer(Long id);
}
