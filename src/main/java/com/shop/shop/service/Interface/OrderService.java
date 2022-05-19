package com.shop.shop.service.Interface;

import com.shop.shop.Dto.Purchase;
import com.shop.shop.entity.Order;
import com.shop.shop.entity.User;

import java.util.List;

public interface OrderService {

    String addNewOrder(Purchase purchase);
    //void updateStatusOrder(Order order);
    List<Order> getAllOrdersByUser(User user);
    Order getOrderById(String order_number);
}
