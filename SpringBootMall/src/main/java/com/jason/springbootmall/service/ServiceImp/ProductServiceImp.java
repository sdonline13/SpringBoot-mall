package com.jason.springbootmall.service.ServiceImp;

import com.jason.springbootmall.dao.ProductDao;
import com.jason.springbootmall.dto.ProductQueryParams;
import com.jason.springbootmall.dto.ProductRequest;
import com.jason.springbootmall.model.Product;
import com.jason.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImp implements ProductService {
    @Autowired
    ProductDao dao;
    @Override
    public Product getById(Integer productId) {
        return dao.getById(productId);
    }

    @Override
    public int create(ProductRequest dto) {
        return dao.create(dto);
    }

    @Override
    public void updateById(Integer id, ProductRequest productRequest) {
        dao.updateById(id,productRequest);
    }

    @Override
    public void deleteById(Integer productId) {
        dao.deleteById(productId);
    }
    @Override
    public List<Product> getProducts(ProductQueryParams productQueryParams) {
       return dao.getProducts(productQueryParams);
    }
}
