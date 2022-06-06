package com.invoice.management.app.service.impl;

import com.invoice.management.app.dto.CustomerDto;
import com.invoice.management.app.entity.Customer;
import com.invoice.management.app.exception.ResourceNotFoundException;
import com.invoice.management.app.repository.CustomerRepository;
import com.invoice.management.app.service.CustomerService;

import com.invoice.management.app.service.mapper.CustomerMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

//    private final ModelMapper mapper;
    private CustomerMapper mapper;

    private CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper mapper) {
        this.customerRepository = customerRepository;
        this.mapper = mapper;
    }

    @Override
    public CustomerDto createCustomer(CustomerDto customerDto) {
        Customer customer = new Customer();
        mapper.mapToEntity(customerDto, customer);

        Customer newCustomer = customerRepository.save(customer);

        mapper.mapToDTO(newCustomer, customerDto);
        return customerDto;
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers
                .stream()
                .map(customer -> {
                    CustomerDto customerDto = new CustomerDto();
                    mapper.mapToDTO(customer, customerDto);

                    return customerDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDto getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id.toString()));

        CustomerDto customerDto = new CustomerDto();
        mapper.mapToDTO(customer, customerDto);

        return customerDto;
    }

    @Override
    public CustomerDto updateCustomer(CustomerDto customerDto, Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id.toString()));

//        customer.setFullName(customerDto.getFullName());
//        customer.setEmail(customerDto.getEmail());
//        customer.setCity(customerDto.getCity());
//        customer.setCountry(customerDto.getCountry());
//        customer.setStreet(customerDto.getStreet());
//        customer.setPhoneNumber(customerDto.getPhoneNumber());
//        customer.setNip(customerDto.getNip());
//        customer.setDescription(customerDto.getDescription());
        mapper.mapToEntity(customerDto, customer);

        Customer updatedCustomer = customerRepository.save(customer);

        CustomerDto updatedCustomerDto = new CustomerDto();
        mapper.mapToDTO(updatedCustomer, updatedCustomerDto);

        return updatedCustomerDto;
    }

    @Override
    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id.toString()));
        customerRepository.delete(customer);
    }

//    private CustomerDto mapToDTO(Customer customer) {
//        return mapper.map(customer, CustomerDto.class);
//    }
//
//    private Customer mapToEntity(CustomerDto customerDto) {
//        return mapper.map(customerDto, Customer.class);
//    }
}
