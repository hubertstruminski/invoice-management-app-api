package com.invoice.management.app.controller;

import com.invoice.management.app.dto.PersistableInvoiceDto;
import com.invoice.management.app.dto.ReadableInvoiceDto;
import com.invoice.management.app.security.JwtTokenProvider;
import com.invoice.management.app.service.InvoiceService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final JwtTokenProvider tokenProvider;

    public InvoiceController(InvoiceService invoiceService, JwtTokenProvider tokenProvider) {
        this.invoiceService = invoiceService;
        this.tokenProvider = tokenProvider;
    }

//    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<ReadableInvoiceDto> createInvoice(@Valid @RequestBody PersistableInvoiceDto invoiceDto,
                                                            @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        Long userId = tokenProvider.getUserIdFromJWT(token.split(" ")[1]);
        return new ResponseEntity<>(invoiceService.createInvoice(invoiceDto, userId), HttpStatus.CREATED);
    }

    @GetMapping
    public List<ReadableInvoiceDto> getAllInvoices(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        Long userId = tokenProvider.getUserIdFromJWT(token.split(" ")[1]);
        return invoiceService.getAllInvoices(userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReadableInvoiceDto> getInvoiceById(@PathVariable(name = "id") Long id,
                                                             @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        Long userId = tokenProvider.getUserIdFromJWT(token.split(" ")[1]);
        return ResponseEntity.ok(invoiceService.getInvoiceById(id, userId));
    }

//    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}")
    public ResponseEntity<ReadableInvoiceDto> updateInvoice(
            @Valid @RequestBody PersistableInvoiceDto invoiceDto,
            @PathVariable(name = "id") Long id) {
        ReadableInvoiceDto invoiceResponse = invoiceService.updateInvoice(invoiceDto, id);
        return new ResponseEntity<>(invoiceResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable(name = "id") Long id) {
        invoiceService.deleteInvoice(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
