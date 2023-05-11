package com.jason.springbootmall.service.ServiceImp;

import com.jason.springbootmall.dao.OrderDao;
import com.jason.springbootmall.dao.ProductDao;
import com.jason.springbootmall.dao.UserDao;
import com.jason.springbootmall.dto.BuyItem;
import com.jason.springbootmall.dto.CreateOrderRequest;
import com.jason.springbootmall.dto.OrderQueryParams;
import com.jason.springbootmall.model.Order;
import com.jason.springbootmall.model.OrderItem;
import com.jason.springbootmall.model.Product;
import com.jason.springbootmall.model.UserOrders;
import com.jason.springbootmall.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImp  implements OrderService {
    private  final  static Logger log= LoggerFactory.getLogger(OrderServiceImp.class);
    @Autowired
    OrderDao orderDao;

    @Autowired
    ProductDao productDao;
    @Autowired
    UserDao userDao;
    @Transactional //防止操作失敗(兩張table需同時完成)
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {
        //檢查User是否存在
        if (userDao.getUserById(userId) == null) {
            log.warn("該user {} 不存在", userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }


        List<OrderItem> orderItemList = new ArrayList<>();

        //計算該筆訂單總花費
        int totalAmount = 0;
        for (BuyItem buyItem : createOrderRequest.getBuyItemList()) {
            Product product = productDao.getById(buyItem.getProductId());

            //檢查商品數量
            if (product == null) {
                log.warn("商品不存在");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            } else if (product.getStock() < buyItem.getQuantity()) {
                log.warn("商品{}庫存不足 ，無法購買 ，剩餘 {}, 欲購買量: {}",
                        buyItem.getProductId(), product.getStock(), buyItem.getQuantity());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            //扣除商品庫存
            productDao.updateStock(product.getProductId(),product.getStock()-buyItem.getQuantity());


            //計算總價錢
            int amount = buyItem.getQuantity() * product.getPrice();
            totalAmount += amount;

            //轉換BuyItem to OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);
            orderItemList.add(orderItem);
        }

        //創立訂單
        Integer orderId = orderDao.createOrder(userId, totalAmount);

        //創立訂單詳細資訊(多筆)
        orderDao.createOrderItems(orderId, orderItemList);

        return orderId;
    }

    //取得訂單資訊
    @Override
    public Order getOrderById(Integer orderId) {
        Order order=orderDao.getOrderById(orderId);
        //拿到商品相關數據
        List<OrderItem> orderItemList =orderDao.getOrderItemsByOrderId(orderId);

        order.setOrderItemList(orderItemList);
        return order;
    }
    @Override
    public List<Order> getOrder(OrderQueryParams orderQueryParams) {
        //根據 orderQueryParams 找出訂單
        List<Order> orderList =orderDao.getOrders(orderQueryParams);
        for (Order val: orderList) {
            //針對每筆訂單 取得對應 OrderItemList
            List<OrderItem> orderItem =orderDao.getOrderItemsByOrderId(val.getOrderId());
            val.setOrderItemList(orderItem);
        }

        return orderList;
    }

    @Override
    public Integer countOrder(OrderQueryParams orderQueryParams) {
        return orderDao.countOrder(orderQueryParams);
    }
}
