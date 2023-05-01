package com.jason.springbootmall.dto;

import com.jason.springbootmall.model.Product;
import lombok.Data;

@Data
public class ProductQueryParams {
    Product.PriductCategory category;
    String search;
}
