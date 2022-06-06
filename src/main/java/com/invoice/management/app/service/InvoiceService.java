package com.invoice.management.app.service;

import com.invoice.management.app.dto.PersistableInvoiceDto;
import com.invoice.management.app.dto.ReadableInvoiceDto;

import java.util.List;

public interface InvoiceService {

    ReadableInvoiceDto createInvoice(PersistableInvoiceDto invoiceDto);

    List<ReadableInvoiceDto> getAllInvoices();

    ReadableInvoiceDto getInvoiceById(Long id);

    ReadableInvoiceDto updateInvoice(PersistableInvoiceDto invoiceDto, Long id);

    void deleteInvoice(Long id);
}
