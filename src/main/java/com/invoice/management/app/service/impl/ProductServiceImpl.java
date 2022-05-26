package com.invoice.management.app.service.impl;

import com.invoice.management.app.dto.ProductDto;
import com.invoice.management.app.entity.Product;
import com.invoice.management.app.exception.ResourceNotFoundException;
import com.invoice.management.app.repository.ProductRepository;
import com.invoice.management.app.service.ProductService;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    private ModelMapper mapper;

    private ProductServiceImpl(ProductRepository productRepository, ModelMapper mapper) {
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product product = mapToEntity(productDto);
        Product newProduct = productRepository.save(product);

        ProductDto productResponse = mapToDTO(newProduct);
        return productResponse;
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(product -> mapToDTO(product)).collect(Collectors.toList());
    }

    @Override
    public ProductDto getProductById(UUID id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product", "id", id.toString()));
        return mapToDTO(product);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, UUID id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product", "id", id.toString()));

        product.setName(productDto.getName());
        product.setAmount(productDto.getAmount());
        product.setDiscount(productDto.getDiscount());
        product.setPrice(productDto.getPrice());
        product.setUnit(productDto.getUnit());
        product.setDescription(productDto.getDescription());

        Product updatedProduct = productRepository.save(product);
        return mapToDTO(updatedProduct);
    }

    @Override
    public void deleteProduct(UUID id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product", "id", id.toString()));
        productRepository.delete(product);
    }

    private ProductDto mapToDTO(Product product) {
        return mapper.map(product, ProductDto.class);
    }

    private Product mapToEntity(ProductDto productDto) {
        return mapper.map(productDto, Product.class);
    }
}
