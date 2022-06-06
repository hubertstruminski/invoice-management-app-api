package com.invoice.management.app.controller;

import com.invoice.management.app.dto.PersistableInvoiceDto;
import com.invoice.management.app.dto.ReadableInvoiceDto;
import com.invoice.management.app.service.InvoiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping
    public ResponseEntity<ReadableInvoiceDto> createInvoice(@Valid @RequestBody PersistableInvoiceDto invoiceDto) {
        return new ResponseEntity<>(invoiceService.createInvoice(invoiceDto), HttpStatus.CREATED);
    }

    @GetMapping
    public List<ReadableInvoiceDto> getAllInvoices() {
        return invoiceService.getAllInvoices();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReadableInvoiceDto> getInvoiceById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(invoiceService.getInvoiceById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReadableInvoiceDto> updateInvoice(
            @Valid @RequestBody PersistableInvoiceDto invoiceDto,
            @PathVariable(name = "id") Long id) {
        ReadableInvoiceDto invoiceResponse = invoiceService.updateInvoice(invoiceDto, id);
        return new ResponseEntity<>(invoiceResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable(name = "id") Long id) {
        invoiceService.deleteInvoice(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
