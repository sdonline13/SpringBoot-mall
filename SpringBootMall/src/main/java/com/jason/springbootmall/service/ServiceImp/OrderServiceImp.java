package com.jason.springbootmall.service.ServiceImp;

import com.jason.springbootmall.dao.OrderDao;
import com.jason.springbootmall.dao.ProductDao;
import com.jason.springbootmall.dto.BuyItem;
import com.jason.springbootmall.dto.CreateOrderRequest;
import com.jason.springbootmall.model.Order;
import com.jason.springbootmall.model.OrderItem;
import com.jason.springbootmall.model.Product;
import com.jason.springbootmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImp  implements OrderService {
    @Autowired
    OrderDao orderDao;

    @Autowired
    ProductDao productDao;
    @Transactional //防止操作失敗(兩張table需同時完成)
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {
        List<OrderItem> orderItemList =new ArrayList<>() ;

        //計算該筆訂單總花費
        int totalAmount=0;
        for(BuyItem buyItem:createOrderRequest.getBuyItemList()){
            Product product =productDao.getById(buyItem.getProductId());//
            //計算總價錢
            int amount = buyItem.getQuantity() * product.getPrice();
            totalAmount +=amount;

            //轉換BuyItem to OrderItem
            OrderItem orderItem=new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);
            orderItemList.add(orderItem);
        }

        //創立訂單
        Integer orderId= orderDao.createOrder(userId,totalAmount);

        //創立訂單詳細資訊(多筆)
        orderDao.createOrderItems(orderId,orderItemList);

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
    public Order getOrderByUserId(Integer userId) {
        Order order=orderDao.getOrderByUserId(userId);
        if(order==null)
            return null;
        //拿到商品相關數據
        List<OrderItem> orderItemList =orderDao.getOrderItemsByOrderId(order.getOrderId());

        order.setOrderItemList(orderItemList);
        return order;
    }
}
