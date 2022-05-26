package com.invoice.management.app.controller;

import com.invoice.management.app.dto.InvoiceDto;
import com.invoice.management.app.service.InvoiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    private InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping
    public ResponseEntity<InvoiceDto> createInvoice(@Valid @RequestBody InvoiceDto invoiceDto) {
        return new ResponseEntity<>(invoiceService.createInvoice(invoiceDto), HttpStatus.CREATED);
    }

    @GetMapping
    public List<InvoiceDto> getAllInvoices() {
        return invoiceService.getAllInvoices();
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceDto> getInvoiceById(@PathVariable(name = "id") UUID id) {
        return ResponseEntity.ok(invoiceService.getInvoiceById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InvoiceDto> updateInvoice(
            @Valid @RequestBody InvoiceDto invoiceDto,
            @PathVariable(name = "id") UUID id) {
        InvoiceDto invoiceResponse = invoiceService.updateInvoice(invoiceDto, id);
        return new ResponseEntity<>(invoiceResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable(name = "id") UUID id) {
        invoiceService.deleteInvoice(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
