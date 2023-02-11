package com.invoice.management.app.controller;

import com.invoice.management.app.dto.PersistableProductDto;
import com.invoice.management.app.dto.ReadableInvoiceDto;
import com.invoice.management.app.dto.ReadableProductDto;
import com.invoice.management.app.security.JwtTokenProvider;
import com.invoice.management.app.service.ProductService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final JwtTokenProvider tokenProvider;

    public ProductController(ProductService productService, JwtTokenProvider tokenProvider) {
        this.productService = productService;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping
    public ResponseEntity<ReadableProductDto> createProduct(@Valid @RequestBody PersistableProductDto persistableProductDto,
                                                            @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        Long userId = tokenProvider.getUserIdFromJWT(token.split(" ")[1]);
        return new ResponseEntity<>(productService.createProduct(persistableProductDto, userId), HttpStatus.CREATED);
    }

    @GetMapping
    public List<ReadableProductDto> getAllProducts(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        Long userId = tokenProvider.getUserIdFromJWT(token.split(" ")[1]);
        return productService.getAllProducts(userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReadableProductDto> getProductById(@PathVariable(name = "id") Long id,
                                                             @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        Long userId = tokenProvider.getUserIdFromJWT(token.split(" ")[1]);
        return ResponseEntity.ok(productService.getProductById(id, userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReadableProductDto> updateProduct(
            @Valid @RequestBody PersistableProductDto persistableProductDto,
            @PathVariable(name = "id") Long id) {
        ReadableProductDto productResponse = productService.updateProduct(persistableProductDto, id);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable(name = "id") Long id,
                                              @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        Long userId = tokenProvider.getUserIdFromJWT(token.split(" ")[1]);

        productService.deleteProduct(id, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
