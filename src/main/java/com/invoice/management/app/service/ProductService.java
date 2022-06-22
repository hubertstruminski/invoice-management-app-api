package com.invoice.management.app.service;

import com.invoice.management.app.dto.PersistableProductDto;
import com.invoice.management.app.dto.ReadableProductDto;

import java.util.List;

public interface ProductService {

    ReadableProductDto createProduct(PersistableProductDto persistableProductDto);

    List<ReadableProductDto> getAllProducts();

    ReadableProductDto getProductById(Long id);

    ReadableProductDto updateProduct(PersistableProductDto persistableProductDto, Long id);

    void deleteProduct(Long id);
}
