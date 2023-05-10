package com.jason.springbootmall.service;

import com.jason.springbootmall.dto.CreateOrderRequest;
import com.jason.springbootmall.model.Order;

public interface OrderService {
    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

    Order getOrderById(Integer orderId);

    Order getOrderByUserId(Integer userId);
}
