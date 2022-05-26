package com.invoice.management.app.service;

import com.invoice.management.app.dto.CompanyDto;
import com.invoice.management.app.dto.InvoiceDto;

import java.util.List;
import java.util.UUID;

public interface InvoiceService {

    InvoiceDto createInvoice(InvoiceDto invoiceDto);

    List<InvoiceDto> getAllInvoices();

    InvoiceDto getInvoiceById(UUID id);

    InvoiceDto updateInvoice(InvoiceDto invoiceDto, UUID id);

    void deleteInvoice(UUID id);
}
