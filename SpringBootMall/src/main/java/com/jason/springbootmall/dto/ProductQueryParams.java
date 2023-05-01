package com.jason.springbootmall.dto;

import com.jason.springbootmall.model.Product;
import lombok.Data;

@Data
public class ProductQueryParams {
    Product.PriductCategory category;
    String search;
    OrderBy orderBy;
    Sort sort;
    public enum  OrderBy{
        last_modified_date,
        price,
        stock
    }
    public   enum  Sort{
        ASC,
        DESC
    }
}
