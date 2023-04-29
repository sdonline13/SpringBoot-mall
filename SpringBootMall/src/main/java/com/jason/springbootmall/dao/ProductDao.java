package com.jason.springbootmall.dao;

import com.jason.springbootmall.model.Product;

public interface ProductDao {
   Product getProductById(Integer id);
}
