package com.invoice.management.app.service.impl;

import com.invoice.management.app.dto.CustomerDto;
import com.invoice.management.app.entity.Customer;
import com.invoice.management.app.entity.User;
import com.invoice.management.app.exception.ResourceNotFoundException;
import com.invoice.management.app.repository.CustomerRepository;
import com.invoice.management.app.repository.UserRepository;
import com.invoice.management.app.service.CustomerService;
import com.invoice.management.app.service.mapper.CustomerMapper;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final CustomerMapper mapper;

    private CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper mapper, UserRepository userRepository) {
        this.customerRepository = customerRepository;
        this.mapper = mapper;
        this.userRepository = userRepository;
    }

    @Override
    public CustomerDto createCustomer(CustomerDto customerDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId.toString()));

        Customer customer = mapper.mapToEntity(customerDto, new Customer());
        customer.setUser(user);

        Customer newCustomer = customerRepository.save(customer);

        return mapper.mapToDTO(newCustomer, new CustomerDto());
    }

    @Override
    public List<CustomerDto> getAllCustomers(Long userId) {
        List<Customer> customers = customerRepository.findAll(userId);
        return customers
                .stream()
                .map(customer -> mapper.mapToDTO(customer, new CustomerDto()))
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDto getCustomerById(Long id, Long userId) {
        Customer customer = customerRepository.findById(id, userId);
        if(customer == null) {
            throw new ResourceNotFoundException("Customer", "id", id.toString());
        }
        return mapper.mapToDTO(customer, new CustomerDto());
    }

    @Override
    public CustomerDto updateCustomer(CustomerDto customerDto, Long id, Long userId) {
        Customer customer = customerRepository.findById(id, userId);
        if(customer == null) {
            throw new ResourceNotFoundException("Customer", "id", id.toString());
        }
        mapper.mapToEntity(customerDto, customer);

        Customer updatedCustomer = customerRepository.save(customer);
        return mapper.mapToDTO(updatedCustomer, new CustomerDto());
    }

    @Override
    public void deleteCustomer(Long id, Long userId) {
        Customer customer = customerRepository.findById(id, userId);
        if(customer == null) {
            throw new ResourceNotFoundException("Customer", "id", id.toString());
        }
        customerRepository.delete(customer);
    }
}
