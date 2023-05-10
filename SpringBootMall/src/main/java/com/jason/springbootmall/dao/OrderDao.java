package com.jason.springbootmall.dao;

import com.jason.springbootmall.model.OrderItem;
import io.swagger.models.auth.In;

import java.util.List;

public interface OrderDao {
    Integer createOrder(Integer userId,Integer totalAmount);

    void createOrderItems(Integer orderId, List<OrderItem> ordItemsList);
}
