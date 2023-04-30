package com.jason.springbootmall.service.ServiceImp;

import com.jason.springbootmall.dao.Dao;
import com.jason.springbootmall.dto.ProductRequest;
import com.jason.springbootmall.model.Product;
import com.jason.springbootmall.service.ServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImp implements ServiceBase<Product, ProductRequest> {
    @Autowired
    Dao<Product,ProductRequest> dao;
    @Override
    public Product getById(Integer productId) {
        return dao.getById(productId);
    }

    @Override
    public int create(ProductRequest dto) {
        return dao.create(dto);
    }
}
