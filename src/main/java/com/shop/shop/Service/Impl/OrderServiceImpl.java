package com.shop.shop.Service.Impl;

import com.shop.shop.Entity.Order;
import com.shop.shop.Entity.Product;
import com.shop.shop.Entity.User;
import com.shop.shop.Repositories.OrderRepository;
import com.shop.shop.Repositories.ProductRepository;
import com.shop.shop.Service.Interface.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Override
    public void addNewOrder(Order order) {
        orderRepository.save(order);
    }


    @Override
    public void updateStatusOrder(Order order) {
        order.setStatus("Oplacone");
        orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrdersByUser(User user) {
        return orderRepository.getAllByUser(user);
    }

}
