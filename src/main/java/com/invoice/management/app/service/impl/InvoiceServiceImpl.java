package com.invoice.management.app.service.impl;

import com.invoice.management.app.dto.PersistableInvoiceDto;
import com.invoice.management.app.dto.ReadableInvoiceDto;
import com.invoice.management.app.entity.Customer;
import com.invoice.management.app.entity.Invoice;
import com.invoice.management.app.entity.Product;
import com.invoice.management.app.exception.ResourceNotFoundException;
import com.invoice.management.app.repository.CustomerRepository;
import com.invoice.management.app.repository.InvoiceRepository;
import com.invoice.management.app.repository.ProductRepository;
import com.invoice.management.app.service.InvoiceService;

import com.invoice.management.app.service.mapper.InvoiceMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
//    private final ModelMapper mapper;

    private InvoiceMapper mapper;

    private InvoiceServiceImpl(InvoiceRepository invoiceRepository,
                               CustomerRepository customerRepository,
                               ProductRepository productRepository,
                               InvoiceMapper mapper) {
        this.invoiceRepository = invoiceRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    @Override
    public ReadableInvoiceDto createInvoice(PersistableInvoiceDto invoiceDto) {
        Long customerId = invoiceDto.getCustomerId();
        Customer customer = customerRepository.findById(customerId).orElseThrow(() ->  new ResourceNotFoundException("Customer", "id", customerId.toString()));

        Invoice invoice = new Invoice();
        mapper.mapPersistableDTOToEntity(invoiceDto, invoice);

        invoice.setCustomer(customer);
        invoice.setProducts(mapIdsToProducts(invoiceDto.getProductIds()));

        Invoice newInvoice = invoiceRepository.save(invoice);

        ReadableInvoiceDto newInvoiceDto = new ReadableInvoiceDto();
        mapper.mapToReadableDTO(newInvoice, newInvoiceDto);

        return newInvoiceDto;
    }

    @Override
    public List<ReadableInvoiceDto> getAllInvoices() {
        List<Invoice> invoices = invoiceRepository.findAll();
//        this::mapToReadableDTO;
        return invoices
                .stream()
                .map(invoice -> {
                    ReadableInvoiceDto invoiceDto = new ReadableInvoiceDto();
                    mapper.mapToReadableDTO(invoice, invoiceDto);

                    return invoiceDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public ReadableInvoiceDto getInvoiceById(Long id) {
        Invoice invoice = invoiceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Invoice", "id", id.toString()));

        ReadableInvoiceDto invoiceDto = new ReadableInvoiceDto();
        mapper.mapToReadableDTO(invoice, invoiceDto);

        return invoiceDto;
    }

    @Override
    public ReadableInvoiceDto updateInvoice(PersistableInvoiceDto invoiceDto, Long id) {
        Invoice invoice = invoiceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Invoice", "id", id.toString()));

        mapper.mapPersistableDTOToEntity(invoiceDto, invoice);
//        customerRepository.findById(invoiceDto.getCustomerId()).orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id.toString()));
//        BeanUtils.copyProperties(invoiceDto, invoice, "id", "products");
//        invoice.setNumber(invoiceDto.getNumber());
//        invoice.setDate(invoiceDto.getDate());
//        invoice.setDeadline(invoiceDto.getDeadline());
//        invoice.setDescription(invoiceDto.getDescription());
//        invoice.setSentStatus(invoiceDto.isSentStatus());
//        invoice.setCustomer(invoiceDto.getCustomerId());

        List<Product> mappedProducts = mapIdsToProducts(invoiceDto.getProductIds());
        List<Product> mergedProducts = mergeProducts(mappedProducts, invoice.getProducts());
        invoice.setProducts(mergedProducts);

        Invoice updatedInvoice = invoiceRepository.save(invoice);

        ReadableInvoiceDto newInvoiceDto = new ReadableInvoiceDto();
        mapper.mapToReadableDTO(updatedInvoice, newInvoiceDto);

        return newInvoiceDto;
    }

    @Override
    public void deleteInvoice(Long id) {
        Invoice invoice = invoiceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Invoice", "id", id.toString()));
        invoiceRepository.delete(invoice);
    }

//    private ReadableInvoiceDto mapToReadableDTO(Invoice invoice) {
//        return mapper.map(invoice, ReadableInvoiceDto.class);
//    }
//
//    private Invoice mapPersistableDTOToEntity(PersistableInvoiceDto invoiceDto) {
//        return mapper.map(invoiceDto, Invoice.class);
//    }

    private List<Product> mapIdsToProducts(List<Long> productIds) {
        return productIds
                .stream()
                .map(productId -> {
                    Optional<Product> foundProduct = productRepository.findById(productId);
                    return foundProduct.get();
                })
                .collect(Collectors.toList());
    }

    private List<Product> mergeProducts(List<Product> first, List<Product> second) {
        Set<Product> collection = new HashSet<>(first);
        for(Product product: second) {
            collection.add(product);
        }

        return collection.stream().collect(Collectors.toList());
    }
}


