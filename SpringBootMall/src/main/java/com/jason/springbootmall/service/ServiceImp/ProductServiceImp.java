package com.jason.springbootmall.service.ServiceImp;

import com.jason.springbootmall.dao.Dao;
import com.jason.springbootmall.model.Product;
import com.jason.springbootmall.service.ServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImp implements ServiceBase<Product> {
    @Autowired
    Dao<Product> dao;
    @Override
    public Product getById(Integer productId) {
        return dao.getById(productId);
    }
}
