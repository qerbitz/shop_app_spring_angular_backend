package com.shop.shop.Service.Interface;

import com.shop.shop.Entity.Order;
import com.shop.shop.Entity.Product;
import com.shop.shop.Entity.User;

import java.util.List;

public interface OrderService {


    void addNewOrder(Order order);
    void updateStatusOrder(Order order);
}
