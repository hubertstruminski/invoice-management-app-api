package com.invoice.management.app.service.impl;

import com.invoice.management.app.dto.InvoiceDto;
import com.invoice.management.app.entity.Invoice;
import com.invoice.management.app.exception.ResourceNotFoundException;
import com.invoice.management.app.repository.InvoiceRepository;
import com.invoice.management.app.service.InvoiceService;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private InvoiceRepository invoiceRepository;

    private ModelMapper mapper;

    private InvoiceServiceImpl(InvoiceRepository invoiceRepository, ModelMapper mapper) {
        this.invoiceRepository = invoiceRepository;
        this.mapper = mapper;
    }

    @Override
    public InvoiceDto createInvoice(InvoiceDto invoiceDto) {
        Invoice invoice = mapToEntity(invoiceDto);
        Invoice newInvoice = invoiceRepository.save(invoice);

        InvoiceDto invoiceResponse = mapToDTO(newInvoice);
        return invoiceResponse;
    }

    @Override
    public List<InvoiceDto> getAllInvoices() {
        List<Invoice> invoices = invoiceRepository.findAll();
        return invoices.stream().map(invoice -> mapToDTO(invoice)).collect(Collectors.toList());
    }

    @Override
    public InvoiceDto getInvoiceById(UUID id) {
        Invoice invoice = invoiceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Invoice", "id", id.toString()));
        return mapToDTO(invoice);
    }

    @Override
    public InvoiceDto updateInvoice(InvoiceDto invoiceDto, UUID id) {
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
    public void deleteInvoice(UUID id) {
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
