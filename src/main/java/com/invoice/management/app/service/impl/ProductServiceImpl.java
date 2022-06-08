package com.invoice.management.app.service.impl;

import com.invoice.management.app.dto.ProductDto;
import com.invoice.management.app.entity.Product;
import com.invoice.management.app.exception.ResourceNotFoundException;
import com.invoice.management.app.repository.ProductRepository;
import com.invoice.management.app.repository.TaxRepository;
import com.invoice.management.app.service.ProductService;
import com.invoice.management.app.service.mapper.ProductMapper;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final TaxRepository taxRepository;
    private final ProductMapper mapper;

    private ProductServiceImpl(ProductRepository productRepository,
                               TaxRepository taxRepository, ProductMapper mapper) {
        this.productRepository = productRepository;
        this.taxRepository = taxRepository;
        this.mapper = mapper;
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Long taxId = productDto.getTaxId();
        taxRepository.findById(taxId).orElseThrow(() ->  new ResourceNotFoundException("Tax", "id", taxId.toString()));

        Product product = mapper.mapToEntity(productDto, new Product());
        Product newProduct = productRepository.save(product);

        return mapper.mapToDTO(newProduct, new ProductDto());
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products
                .stream()
                .map(product -> mapper.mapToDTO(product, new ProductDto()))
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product", "id", id.toString()));
        return mapper.mapToDTO(product, new ProductDto());
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product", "id", id.toString()));
        mapper.mapToEntity(productDto, product);

        Product updatedProduct = productRepository.save(product);

        return mapper.mapToDTO(updatedProduct, new ProductDto());
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product", "id", id.toString()));
        productRepository.delete(product);
    }
}
