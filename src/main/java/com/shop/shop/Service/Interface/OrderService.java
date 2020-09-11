package com.shop.shop.Service.Interface;

import com.shop.shop.Entity.Cart;
import com.shop.shop.Entity.Order;
import com.shop.shop.Entity.User;

import java.util.List;

public interface OrderService {


    void addNewOrder(Order order);
    void updateStatusOrder(Order order);
    List<Order> getAllOrdersByUser(User user);
    Order getOrderById(int id_order);
}
