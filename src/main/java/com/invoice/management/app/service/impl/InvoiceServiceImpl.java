package com.invoice.management.app.service.impl;

import com.invoice.management.app.dto.InvoiceDto;
import com.invoice.management.app.entity.Customer;
import com.invoice.management.app.entity.Invoice;
import com.invoice.management.app.exception.ResourceNotFoundException;
import com.invoice.management.app.repository.CustomerRepository;
import com.invoice.management.app.repository.InvoiceRepository;
import com.invoice.management.app.service.InvoiceService;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final CustomerRepository customerRepository;

    private final ModelMapper mapper;

    private InvoiceServiceImpl(InvoiceRepository invoiceRepository,
                               CustomerRepository customerRepository, ModelMapper mapper) {
        this.invoiceRepository = invoiceRepository;
        this.customerRepository = customerRepository;
        this.mapper = mapper;
    }

    @Override
    public InvoiceDto createInvoice(InvoiceDto invoiceDto) {
        Long customerId = invoiceDto.getCustomerId();
        Customer customer = customerRepository.findById(customerId).orElseThrow(() ->  new ResourceNotFoundException("Customer", "id", customerId.toString()));

        Invoice invoice = mapToEntity(invoiceDto);
        invoice.setCustomer(customer);
        Invoice newInvoice = invoiceRepository.save(invoice);

        return mapToDTO(newInvoice);
    }

    @Override
    public List<InvoiceDto> getAllInvoices() {
        List<Invoice> invoices = invoiceRepository.findAll();
        return invoices.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public InvoiceDto getInvoiceById(Long id) {
        Invoice invoice = invoiceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Invoice", "id", id.toString()));
        return mapToDTO(invoice);
    }

    @Override
    public InvoiceDto updateInvoice(InvoiceDto invoiceDto, Long id) {
        Invoice invoice = invoiceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Invoice", "id", id.toString()));

        invoice.setNumber(invoiceDto.getNumber());
        invoice.setDate(invoiceDto.getDate());
        invoice.setDeadline(invoiceDto.getDeadline());
        invoice.setDescription(invoiceDto.getDescription());
        invoice.setSentStatus(invoiceDto.isSentStatus());

        Invoice updatedInvoice = invoiceRepository.save(invoice);
        return mapToDTO(updatedInvoice);
    }

    @Override
    public void deleteInvoice(Long id) {
        Invoice invoice = invoiceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Invoice", "id", id.toString()));
        invoiceRepository.delete(invoice);
    }

    private InvoiceDto mapToDTO(Invoice invoice) {
        return mapper.map(invoice, InvoiceDto.class);
    }

    private Invoice mapToEntity(InvoiceDto invoiceDto) {
        return mapper.map(invoiceDto, Invoice.class);
    }
}
