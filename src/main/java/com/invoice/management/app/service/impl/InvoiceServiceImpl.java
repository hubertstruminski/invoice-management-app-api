package com.invoice.management.app.service.impl;

import com.invoice.management.app.dto.PersistableInvoiceDto;
import com.invoice.management.app.dto.ReadableInvoiceDto;
import com.invoice.management.app.entity.Invoice;
import com.invoice.management.app.entity.Product;
import com.invoice.management.app.exception.ResourceNotFoundException;
import com.invoice.management.app.repository.CustomerRepository;
import com.invoice.management.app.repository.InvoiceRepository;
import com.invoice.management.app.service.InvoiceService;
import com.invoice.management.app.service.mapper.InvoiceMapper;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final CustomerRepository customerRepository;
    private final InvoiceMapper mapper;

    private InvoiceServiceImpl(InvoiceRepository invoiceRepository,
                               CustomerRepository customerRepository,
                               InvoiceMapper mapper) {
        this.invoiceRepository = invoiceRepository;
        this.customerRepository = customerRepository;
        this.mapper = mapper;
    }

    @Override
    public ReadableInvoiceDto createInvoice(PersistableInvoiceDto invoiceDto) {
        Long customerId = invoiceDto.getCustomerId();
        customerRepository.findById(customerId).orElseThrow(() ->  new ResourceNotFoundException("Customer", "id", customerId.toString()));

        Invoice invoice = mapper.mapPersistableDTOToEntity(invoiceDto, new Invoice());
        Invoice newInvoice = invoiceRepository.save(invoice);

        return mapper.mapToReadableDTO(newInvoice, new ReadableInvoiceDto());
    }

    @Override
    public List<ReadableInvoiceDto> getAllInvoices() {
        List<Invoice> invoices = invoiceRepository.findAll();
        return invoices
                .stream()
                .map(invoice -> mapper.mapToReadableDTO(invoice, new ReadableInvoiceDto()))
                .collect(Collectors.toList());
    }

    @Override
    public ReadableInvoiceDto getInvoiceById(Long id) {
        Invoice invoice = invoiceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Invoice", "id", id.toString()));
        return mapper.mapToReadableDTO(invoice, new ReadableInvoiceDto());
    }

    @Override
    public ReadableInvoiceDto updateInvoice(PersistableInvoiceDto invoiceDto, Long id) {
        Invoice invoice = invoiceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Invoice", "id", id.toString()));
        List<Product> beforeProducts = invoice.getProducts();

        mapper.mapPersistableDTOToEntity(invoiceDto, invoice);

        List<Product> mergedProducts = mergeProducts(beforeProducts, invoice.getProducts());
        invoice.setProducts(mergedProducts);
        Invoice updatedInvoice = invoiceRepository.save(invoice);

        return mapper.mapToReadableDTO(updatedInvoice, new ReadableInvoiceDto());
    }

    @Override
    public void deleteInvoice(Long id) {
        Invoice invoice = invoiceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Invoice", "id", id.toString()));
        invoiceRepository.delete(invoice);
    }

    private List<Product> mergeProducts(List<Product> first, List<Product> second) {
        Set<Product> collection = new HashSet<>(first);
        collection.addAll(second);

        return new ArrayList<>(collection);
    }
}


