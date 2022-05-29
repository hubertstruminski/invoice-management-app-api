package com.invoice.management.app.service;

import com.invoice.management.app.dto.ProductDto;

import java.util.List;

public interface ProductService {

    ProductDto createProduct(ProductDto productDto);

    List<ProductDto> getAllProducts();

    ProductDto getProductById(Long id);

    ProductDto updateProduct(ProductDto productDto, Long id);

    void deleteProduct(Long id);
}
