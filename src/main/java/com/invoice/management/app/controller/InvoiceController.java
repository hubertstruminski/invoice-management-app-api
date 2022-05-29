package com.invoice.management.app.controller;

import com.invoice.management.app.dto.InvoiceDto;
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
    public ResponseEntity<InvoiceDto> createInvoice(@Valid @RequestBody InvoiceDto invoiceDto) {
        return new ResponseEntity<>(invoiceService.createInvoice(invoiceDto), HttpStatus.CREATED);
    }

    @GetMapping
    public List<InvoiceDto> getAllInvoices() {
        return invoiceService.getAllInvoices();
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceDto> getInvoiceById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(invoiceService.getInvoiceById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InvoiceDto> updateInvoice(
            @Valid @RequestBody InvoiceDto invoiceDto,
            @PathVariable(name = "id") Long id) {
        InvoiceDto invoiceResponse = invoiceService.updateInvoice(invoiceDto, id);
        return new ResponseEntity<>(invoiceResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable(name = "id") Long id) {
        invoiceService.deleteInvoice(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
