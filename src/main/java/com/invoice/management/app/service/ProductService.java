package com.invoice.management.app.service;

import com.invoice.management.app.dto.CompanyDto;
import com.invoice.management.app.dto.ProductDto;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    ProductDto createProduct(ProductDto productDto);

    List<ProductDto> getAllProducts();

    ProductDto getProductById(UUID id);

    ProductDto updateProduct(ProductDto productDto, UUID id);

    void deleteProduct(UUID id);
}
