package com.shop.shop.service.Interface;

import com.shop.shop.entity.Order;
import com.shop.shop.entity.User;

import java.util.List;

public interface OrderService {

    void addNewOrder(Order order);
    //void updateStatusOrder(Order order);
    List<Order> getAllOrdersByUser(User user);
    Order getOrderById(String id_order);
}
