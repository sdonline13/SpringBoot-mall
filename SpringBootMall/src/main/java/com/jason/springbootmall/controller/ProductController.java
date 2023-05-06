package com.jason.springbootmall.controller;

import com.jason.springbootmall.dto.ProductQueryParams;
import com.jason.springbootmall.dto.ProductRequest;
import com.jason.springbootmall.model.Product;
import com.jason.springbootmall.service.ProductService;
import com.jason.springbootmall.util.Page;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Validated
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
    public  ResponseEntity<Page<Product>>getProduct(
            //查詢條件
            @RequestParam(required = false)String search,
            @RequestParam(required = false) Product.PriductCategory category,
            //排序
            @RequestParam(defaultValue ="last_modified_date") ProductQueryParams.OrderBy orderBy,
            @RequestParam(defaultValue ="DESC") ProductQueryParams.Sort sort,
            //分頁
            @RequestParam(defaultValue="5")   @Max(100) @Min(0)  Integer limit,//取得幾筆
            @RequestParam(defaultValue = "0") @Min(0)   Integer offset//跳過幾筆
    ){
        ProductQueryParams productQueryParams= new ProductQueryParams();
        productQueryParams.setCategory(category);
        productQueryParams.setSearch(search);
        productQueryParams.setOrderBy(orderBy);
        productQueryParams.setSort(sort);
        productQueryParams.setLimit(limit);
        productQueryParams.setOffset(offset);
        List<Product> products;
        products= productService.getProducts(productQueryParams);
        //取得商品總筆 數
        Integer total =productService.countProduct(productQueryParams);
        //設定返回訊息
        Page<Product>  page=new Page<>();
        page.setLimit(limit);
        page.setTotal(total);//當前查詢總共有多少筆數據
        page.setOffset(offset);
        page.setResult(products);
        return ResponseEntity.ok().body(page);
    }

}
