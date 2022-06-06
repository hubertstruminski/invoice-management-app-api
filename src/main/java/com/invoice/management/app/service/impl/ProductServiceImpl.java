package com.invoice.management.app.service.impl;

import com.invoice.management.app.dto.ProductDto;
import com.invoice.management.app.entity.Product;
import com.invoice.management.app.entity.Tax;
import com.invoice.management.app.exception.ResourceNotFoundException;
import com.invoice.management.app.repository.ProductRepository;
import com.invoice.management.app.repository.TaxRepository;
import com.invoice.management.app.service.ProductService;

import com.invoice.management.app.service.mapper.ProductMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final TaxRepository taxRepository;

//    private final ModelMapper mapper;
    private ProductMapper mapper;

    private ProductServiceImpl(ProductRepository productRepository,
                               TaxRepository taxRepository, ProductMapper mapper) {
        this.productRepository = productRepository;
        this.taxRepository = taxRepository;
        this.mapper = mapper;
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Long taxId = productDto.getTaxId();
        Tax tax = taxRepository.findById(taxId).orElseThrow(() ->  new ResourceNotFoundException("Tax", "id", taxId.toString()));

        Product product = new Product();
        mapper.mapToEntity(productDto, product);
        product.setTax(tax);

        Product newProduct = productRepository.save(product);

        ProductDto newProductDto = new ProductDto();
        mapper.mapToDTO(newProduct, newProductDto);

        return newProductDto;
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products
                .stream()
                .map(product -> {
                    ProductDto productDto = new ProductDto();
                    mapper.mapToDTO(product, productDto);

                    return productDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product", "id", id.toString()));

        ProductDto productDto = new ProductDto();
        mapper.mapToDTO(product, productDto);

        return productDto;
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product", "id", id.toString()));

//        product.setName(productDto.getName());
//        product.setAmount(productDto.getAmount());
//        product.setDiscount(productDto.getDiscount());
//        product.setPrice(productDto.getPrice());
//        product.setUnit(productDto.getUnit());
//        product.setDescription(productDto.getDescription());
        mapper.mapToEntity(productDto, product);
        Product updatedProduct = productRepository.save(product);

        ProductDto updatedProductDto = new ProductDto();
        mapper.mapToDTO(updatedProduct, updatedProductDto);

        return updatedProductDto;
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product", "id", id.toString()));
        productRepository.delete(product);
    }

//    private ProductDto mapToDTO(Product product) {
//        return mapper.map(product, ProductDto.class);
//    }
//
//    private Product mapToEntity(ProductDto productDto) {
//        return mapper.map(productDto, Product.class);
//    }
}
