package com.invoice.management.app.controller;

import com.invoice.management.app.dto.CustomerDto;
import com.invoice.management.app.security.JwtTokenProvider;
import com.invoice.management.app.service.CustomerService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final JwtTokenProvider tokenProvider;

    public CustomerController(CustomerService customerService, JwtTokenProvider tokenProvider) {
        this.customerService = customerService;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping
    public ResponseEntity<CustomerDto> createCustomer(@Valid @RequestBody CustomerDto customerDto,
                                                      @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        Long userId = tokenProvider.getUserIdFromJWT(token.split(" ")[1]);
        return new ResponseEntity<>(customerService.createCustomer(customerDto, userId), HttpStatus.CREATED);
    }

    @GetMapping
    public List<CustomerDto> getAllCustomers(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        Long userId = tokenProvider.getUserIdFromJWT(token.split(" ")[1]);
        return customerService.getAllCustomers(userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable(name = "id") Long id,
                                                       @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        Long userId = tokenProvider.getUserIdFromJWT(token.split(" ")[1]);
        return ResponseEntity.ok(customerService.getCustomerById(id, userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> updateCustomer(
            @Valid @RequestBody CustomerDto customerDto,
            @PathVariable(name = "id") Long id) {
        CustomerDto customerResponse = customerService.updateCustomer(customerDto, id);
        return new ResponseEntity<>(customerResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable(name = "id") Long id,
                                               @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        Long userId = tokenProvider.getUserIdFromJWT(token.split(" ")[1]);
        customerService.deleteCustomer(id, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
