package com.shop.shop.service.Impl;

import com.shop.shop.entity.Order;
import com.shop.shop.entity.User;
import com.shop.shop.repositories.OrderRepository;
import com.shop.shop.service.Interface.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Override
    public void addNewOrder(Order order) {

        for(int i=0; i<order.getOrderItems().size(); i++){
            order.getOrderItems().get(i).setOrder(order);
        }

        order.setOrderDate(convertDate(LocalDate.now()));
        order.setOrderTrackingNumber(generateOrderTrackingNumber());
        order.setStatus("Oczekujący");

        orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrdersByUser(User user) {
       // return orderRepository.getAllByUserOrderByOrderDateDesc(user);
        return null;
    }

    @Override
    public Order getOrderById(String id_order) {
        return orderRepository.getOrderByOrderTrackingNumber(id_order);
    }

    public Date convertDate(LocalDate dateToConvert) {
        return java.sql.Date.valueOf(dateToConvert);
    }

    private String generateOrderTrackingNumber() {
        return UUID.randomUUID().toString();
    }
}
