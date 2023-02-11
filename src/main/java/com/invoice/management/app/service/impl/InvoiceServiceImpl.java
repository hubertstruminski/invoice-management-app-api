package com.invoice.management.app.service.impl;

import com.invoice.management.app.dto.PersistableInvoiceDto;
import com.invoice.management.app.dto.ReadableInvoiceDto;
import com.invoice.management.app.entity.Invoice;
import com.invoice.management.app.entity.Product;
import com.invoice.management.app.entity.User;
import com.invoice.management.app.exception.ResourceNotFoundException;
import com.invoice.management.app.repository.CustomerRepository;
import com.invoice.management.app.repository.InvoiceRepository;
import com.invoice.management.app.repository.UserRepository;
import com.invoice.management.app.service.InvoiceService;
import com.invoice.management.app.service.mapper.InvoiceMapper;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final InvoiceMapper mapper;

    private InvoiceServiceImpl(InvoiceRepository invoiceRepository,
                               CustomerRepository customerRepository,
                               InvoiceMapper mapper,
                               UserRepository userRepository) {
        this.invoiceRepository = invoiceRepository;
        this.customerRepository = customerRepository;
        this.mapper = mapper;
        this.userRepository = userRepository;
    }

    @Override
    public ReadableInvoiceDto createInvoice(PersistableInvoiceDto invoiceDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId.toString()));

        Long customerId = invoiceDto.getCustomerId();
        customerRepository.findById(customerId).orElseThrow(() ->  new ResourceNotFoundException("Customer", "id", customerId.toString()));

        Invoice invoice = mapper.mapPersistableDTOToEntity(invoiceDto, new Invoice());
        invoice.setUser(user);

        Invoice newInvoice = invoiceRepository.save(invoice);

        return mapper.mapToReadableDTO(newInvoice, new ReadableInvoiceDto());
    }

    @Override
    public List<ReadableInvoiceDto> getAllInvoices(Long userId) {
        List<Invoice> invoices = invoiceRepository.findAll(userId);
        return invoices
                .stream()
                .map(invoice -> mapper.mapToReadableDTO(invoice, new ReadableInvoiceDto()))
                .collect(Collectors.toList());
    }

    @Override
    public ReadableInvoiceDto getInvoiceById(Long id, Long userId) {
        Invoice invoice = invoiceRepository.findById(id, userId);
        if(invoice == null) {
            throw new ResourceNotFoundException("Invoice", "id", id.toString());
        }
        return mapper.mapToReadableDTO(invoice, new ReadableInvoiceDto());
    }

    @Override
    public ReadableInvoiceDto updateInvoice(PersistableInvoiceDto invoiceDto, Long id, Long userId) {
        Invoice invoice = invoiceRepository.findById(id, userId);
        if(invoice == null) {
            throw new ResourceNotFoundException("Invoice", "id", id.toString());
        }
        Set<Product> beforeProducts = invoice.getProducts();

        mapper.mapPersistableDTOToEntity(invoiceDto, invoice);

        Set<Product> mergedProducts = mergeProducts(beforeProducts, invoice.getProducts());
        invoice.setProducts(mergedProducts);
        Invoice updatedInvoice = invoiceRepository.save(invoice);

        return mapper.mapToReadableDTO(updatedInvoice, new ReadableInvoiceDto());
    }

    @Override
    public void deleteInvoice(Long id, Long userId) {
        Invoice invoice = invoiceRepository.findById(id, userId);
        if(invoice == null) {
            throw new ResourceNotFoundException("Invoice", "id", id.toString());
        }
        invoiceRepository.delete(invoice);
    }

    private Set<Product> mergeProducts(Set<Product> first, Set<Product> second) {
        Set<Product> collection = new HashSet<>(first);
        collection.addAll(second);

        return new HashSet<>(collection);
    }
}


