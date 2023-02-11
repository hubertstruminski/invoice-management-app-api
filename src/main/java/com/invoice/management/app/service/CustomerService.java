package com.invoice.management.app.service;

import com.invoice.management.app.dto.CustomerDto;

import java.util.List;

public interface CustomerService {

    CustomerDto createCustomer(CustomerDto customerDto, Long userId);

    List<CustomerDto> getAllCustomers(Long userId);

    CustomerDto getCustomerById(Long id, Long userId);

    CustomerDto updateCustomer(CustomerDto customerDto, Long id);

    void deleteCustomer(Long id);
}
