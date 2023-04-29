package com.jason.springbootmall.dao.impl;

import com.jason.springbootmall.dao.ProductDao;
import com.jason.springbootmall.dao.rowMapper.ProductRowMapper;
import com.jason.springbootmall.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProductDaoImpl  implements ProductDao {
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Override
    public Product getProductById(Integer productId) {
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
        ProductRowMapper productRowMapper=new ProductRowMapper();
        List<Product> product= namedParameterJdbcTemplate.query(sql, map, productRowMapper);
        if(product.size() > 0)
            return product.get(0);
        return null;
    }
}
