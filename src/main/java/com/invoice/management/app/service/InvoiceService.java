package com.invoice.management.app.service;

import com.invoice.management.app.dto.InvoiceDto;

import java.util.List;

public interface InvoiceService {

    InvoiceDto createInvoice(InvoiceDto invoiceDto);

    List<InvoiceDto> getAllInvoices();

    InvoiceDto getInvoiceById(Long id);

    InvoiceDto updateInvoice(InvoiceDto invoiceDto, Long id);

    void deleteInvoice(Long id);
}
