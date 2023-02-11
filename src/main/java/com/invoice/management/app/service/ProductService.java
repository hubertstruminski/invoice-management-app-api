package com.invoice.management.app.service;

import com.invoice.management.app.dto.PersistableProductDto;
import com.invoice.management.app.dto.ReadableProductDto;

import java.util.List;

public interface ProductService {

    ReadableProductDto createProduct(PersistableProductDto persistableProductDto, Long userId);

    List<ReadableProductDto> getAllProducts(Long userId);

    ReadableProductDto getProductById(Long id, Long userId);

    ReadableProductDto updateProduct(PersistableProductDto persistableProductDto, Long id, Long userId);

    void deleteProduct(Long id, Long userId);
}
