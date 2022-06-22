package com.invoice.management.app.service.impl;

import com.invoice.management.app.dto.PersistableProductDto;
import com.invoice.management.app.dto.ReadableProductDto;
import com.invoice.management.app.entity.Invoice;
import com.invoice.management.app.entity.Product;
import com.invoice.management.app.exception.ResourceNotFoundException;
import com.invoice.management.app.repository.InvoiceRepository;
import com.invoice.management.app.repository.ProductRepository;
import com.invoice.management.app.repository.TaxRepository;
import com.invoice.management.app.service.ProductService;
import com.invoice.management.app.service.mapper.ProductMapper;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final TaxRepository taxRepository;
    private final InvoiceRepository invoiceRepository;
    private final ProductMapper mapper;

    public ProductServiceImpl(ProductRepository productRepository,
                               TaxRepository taxRepository,
                              InvoiceRepository invoiceRepository,
                              ProductMapper mapper) {
        this.productRepository = productRepository;
        this.taxRepository = taxRepository;
        this.invoiceRepository = invoiceRepository;
        this.mapper = mapper;
    }

    @Override
    public ReadableProductDto createProduct(PersistableProductDto persistableProductDto) {
        Long taxId = persistableProductDto.getTaxId();
        taxRepository.findById(taxId).orElseThrow(() ->  new ResourceNotFoundException("Tax", "id", taxId.toString()));

        Product product = mapper.mapToEntity(persistableProductDto, new Product());
        Product newProduct = productRepository.save(product);

        return mapper.mapToDTO(newProduct, new ReadableProductDto());
    }

    @Override
    public List<ReadableProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products
                .stream()
                .map(product -> mapper.mapToDTO(product, new ReadableProductDto()))
                .collect(Collectors.toList());
    }

    @Override
    public ReadableProductDto getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product", "id", id.toString()));
        return mapper.mapToDTO(product, new ReadableProductDto());
    }

    @Override
    public ReadableProductDto updateProduct(PersistableProductDto persistableProductDto, Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product", "id", id.toString()));
        mapper.mapToEntity(persistableProductDto, product);

        Product updatedProduct = productRepository.save(product);

        return mapper.mapToDTO(updatedProduct, new ReadableProductDto());
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product", "id", id.toString()));

        for(Invoice invoice: product.getInvoices()) {
            invoice.removeProduct(product);
        }
        productRepository.delete(product);
    }
}
