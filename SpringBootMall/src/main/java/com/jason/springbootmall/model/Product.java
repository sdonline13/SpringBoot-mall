package com.jason.springbootmall.model;

import lombok.Data;

import java.util.Date;

@Data
public class Product {
    private Integer productId;
    private String  product_name;
    private String  category;
    private String  imageUrl;
    private Integer price;
    private Integer stock;
    private String  description;
    private Date    createdDate;
    private Date    lastModifiedDate;

}
