package com.jason.springbootmall.dto;

import com.jason.springbootmall.model.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;



@Data

public class ProductRequest {
    @NotBlank
    private String  productName;
    @NotNull
    private Product.ProductCategory category;
    @NotBlank
    private String  imageUrl;
    @NotNull
    private Integer price;
    @NotNull
    private Integer stock;

    private String  description;
}
