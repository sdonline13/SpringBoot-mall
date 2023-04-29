package com.jason.springbootmall.controller;

import com.jason.springbootmall.model.Product;
import com.jason.springbootmall.service.ServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
    @Autowired
    ServiceBase<Product> service;
    @GetMapping("/product/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable(name = "productId") int productId) {

        Product rs= service.getById(productId);
        if(rs == null)
            return  ResponseEntity.notFound().build();
        return  ResponseEntity.ok().body(rs);
    }
}
