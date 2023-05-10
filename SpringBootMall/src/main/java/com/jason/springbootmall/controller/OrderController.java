package com.jason.springbootmall.controller;

import com.jason.springbootmall.dto.CreateOrderRequest;
import com.jason.springbootmall.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    @Autowired
    OrderService orderService;
    @PostMapping("/users/{userId}/orders")
    public ResponseEntity<?>createOrder(@PathVariable(name = "userId")int userId,
                                        @RequestBody @Valid CreateOrderRequest createOrderRequest){
       Integer orderId= orderService.createOrder(userId,createOrderRequest);//得到資料庫創建的orderId
    return ResponseEntity.status(HttpStatus.CREATED).body(orderId);
    }
}
