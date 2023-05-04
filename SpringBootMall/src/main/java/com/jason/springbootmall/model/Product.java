package com.jason.springbootmall.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import  java.util.Date;

@Data
public class Product {
    private Integer productId;
    private String  productName;
    private PriductCategory  category;
    private String  imageUrl;
    private Integer price;
    private Integer stock;
    private String  description;
  // @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date    createdDate;
  // @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date    lastModifiedDate;

    public enum  PriductCategory{
     FOOD,CAR,BOOK
    }


}

