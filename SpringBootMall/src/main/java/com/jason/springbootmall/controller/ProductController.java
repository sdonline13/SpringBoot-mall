package com.jason.springbootmall.controller;

import com.jason.springbootmall.dto.ProductRequest;
import com.jason.springbootmall.model.Product;
import com.jason.springbootmall.service.ServiceBase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {
    @Autowired
    ServiceBase<Product,ProductRequest> productService;
    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable(name = "productId") int productId) {

        Product rs= productService.getById(productId);
        if(rs == null)
            return  ResponseEntity.notFound().build();
        return  ResponseEntity.ok().body(rs);
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest  request) {
        Integer productId= productService.create(request);
        Product rs=productService.getById(productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(rs);
    }
}
