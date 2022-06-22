package com.invoice.management.app.controller;

import com.invoice.management.app.dto.PersistableProductDto;
import com.invoice.management.app.dto.ReadableProductDto;
import com.invoice.management.app.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ReadableProductDto> createProduct(@Valid @RequestBody PersistableProductDto persistableProductDto) {
        return new ResponseEntity<>(productService.createProduct(persistableProductDto), HttpStatus.CREATED);
    }

    @GetMapping
    public List<ReadableProductDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReadableProductDto> getProductById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReadableProductDto> updateProduct(
            @Valid @RequestBody PersistableProductDto persistableProductDto,
            @PathVariable(name = "id") Long id) {
        ReadableProductDto productResponse = productService.updateProduct(persistableProductDto, id);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable(name = "id") Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
