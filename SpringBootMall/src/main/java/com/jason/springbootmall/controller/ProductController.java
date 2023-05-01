package com.jason.springbootmall.controller;

import com.jason.springbootmall.dto.ProductQueryParams;
import com.jason.springbootmall.dto.ProductRequest;
import com.jason.springbootmall.model.Product;
import com.jason.springbootmall.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    @Autowired
    ProductService productService;
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

    @PutMapping ("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable(name = "productId")Integer productId,
                                                 @RequestBody @Valid ProductRequest  request) {
        Product check=productService.getById(productId);
        if(check == null)//先檢查商品是否存在
            return ResponseEntity.badRequest().build();
        productService.updateById(productId,request);
        Product rs=productService.getById(productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(rs);
    }
    @DeleteMapping ("/products/{productId}")
    public  ResponseEntity<?> deleteProduct(@PathVariable(name = "productId")Integer productId){
        Product check=productService.getById(productId);
        if(check == null)//先檢查商品是否存在
            return ResponseEntity.badRequest().build();
        productService.deleteById(productId);
        return ResponseEntity.status(200).build();
    }




    @GetMapping("/products")
    public  ResponseEntity<List<Product>>getProduct(
            @RequestParam(required = false)String search,
            @RequestParam(required = false) Product.PriductCategory category,
            @RequestParam(defaultValue ="last_modified_date") ProductQueryParams.OrderBy orderBy,
            @RequestParam(defaultValue ="DESC") ProductQueryParams.Sort sort
    ){
        ProductQueryParams productQueryParams= new ProductQueryParams();
        productQueryParams.setCategory(category);
        productQueryParams.setSearch(search);
        productQueryParams.setOrderBy(orderBy);
        productQueryParams.setSort(sort);
        List<Product> products;
        products= productService.getProducts(productQueryParams);
        return ResponseEntity.ok().body(products);
    }

}
