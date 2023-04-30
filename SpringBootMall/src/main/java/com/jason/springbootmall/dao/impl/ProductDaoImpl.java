package com.jason.springbootmall.dao.impl;

import com.jason.springbootmall.dao.Dao;
import com.jason.springbootmall.dao.rowMapper.ProductRowMapper;
import com.jason.springbootmall.dto.ProductRequest;
import com.jason.springbootmall.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProductDaoImpl  implements Dao<Product, ProductRequest> {
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Override
    public Product getById(Integer productId) {
        String sql ="select  " +
                "product_id," +
                "product_name, " +
                "category, " +
                "image_url, " +
                "price, stock, " +
                "description, " +
                "created_date, " +
                "last_modified_date " +
                "from  product " +
                "where product_id=:productId";
        Map<String, Object> map = new HashMap<>();
        map.put("productId",productId);
        List<Product> product= namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());
        if(product.size() > 0)
            return product.get(0);
        return null;
    }

    @Override
    public Integer create(ProductRequest productRequest) {
        String  sql ="INSERT  INTO product(PRODUCT_NAME, " +
                "CATEGORY, " +
                "IMAGE_URL," +
                " PRICE, " +
                "STOCK, " +
                "DESCRIPTION, " +
                "CREATED_DATE, " +
                "LAST_MODIFIED_DATE) " +
                "VALUES(" +
                ":produceName," +
                ":category," +
                ":imageUrl," +
                ":price," +
                ":stock," +
                ":description," +
                ":createDate," +
                ":lastModifiedDate" +
                ")";
        Map<String, Object> map = new HashMap<>();
        map.put("produceName",productRequest.getProductName());
        map.put("category",productRequest.getCategory().toString());
        map.put("imageUrl",productRequest.getImageUrl());
        map.put("price",productRequest.getPrice());
        map.put("stock",productRequest.getStock());
        map.put("description",productRequest.getDescription());
        Date now = new Date();
        map.put("createDate",now);
        map.put("lastModifiedDate",now);

        KeyHolder key=new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),key);
       int id=key.getKey().intValue();
       return id;
    }
}
