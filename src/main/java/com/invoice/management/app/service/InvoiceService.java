package com.invoice.management.app.service;

import com.invoice.management.app.dto.PersistableInvoiceDto;
import com.invoice.management.app.dto.ReadableInvoiceDto;

import java.util.List;

public interface InvoiceService {

    ReadableInvoiceDto createInvoice(PersistableInvoiceDto invoiceDto, Long userId);

    List<ReadableInvoiceDto> getAllInvoices(Long userId);

    ReadableInvoiceDto getInvoiceById(Long id, Long userId);

    ReadableInvoiceDto updateInvoice(PersistableInvoiceDto invoiceDto, Long id);

    void deleteInvoice(Long id);
}
