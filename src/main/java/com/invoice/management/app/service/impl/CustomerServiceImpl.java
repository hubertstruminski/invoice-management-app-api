package com.invoice.management.app.service.impl;

import com.invoice.management.app.dto.CustomerDto;
import com.invoice.management.app.entity.Customer;
import com.invoice.management.app.exception.ResourceNotFoundException;
import com.invoice.management.app.repository.CustomerRepository;
import com.invoice.management.app.service.CustomerService;
import com.invoice.management.app.service.mapper.CustomerMapper;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper mapper;

    private CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper mapper) {
        this.customerRepository = customerRepository;
        this.mapper = mapper;
    }

    @Override
    public CustomerDto createCustomer(CustomerDto customerDto) {
        Customer customer = mapper.mapToEntity(customerDto, new Customer());

        Customer newCustomer = customerRepository.save(customer);

        return mapper.mapToDTO(newCustomer, new CustomerDto());
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers
                .stream()
                .map(customer -> mapper.mapToDTO(customer, new CustomerDto()))
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDto getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id.toString()));
        return mapper.mapToDTO(customer, new CustomerDto());
    }

    @Override
    public CustomerDto updateCustomer(CustomerDto customerDto, Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id.toString()));
        mapper.mapToEntity(customerDto, customer);

        Customer updatedCustomer = customerRepository.save(customer);
        return mapper.mapToDTO(updatedCustomer, new CustomerDto());
    }

    @Override
    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id.toString()));
        customerRepository.delete(customer);
    }
}
