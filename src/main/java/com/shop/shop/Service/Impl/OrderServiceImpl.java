package com.shop.shop.Service.Impl;

import com.shop.shop.Entity.Order;
import com.shop.shop.Entity.Product;
import com.shop.shop.Repositories.OrderRepository;
import com.shop.shop.Service.Interface.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Override
    public void addOrder(List<Product> theList) {

        Product product = new Product();
        product.setName(theList.get(0).getName());

        Order order = new Order();
        Date date = new Date();
        order.setOrderDate(convertDate(LocalDate.now()));

        //orderRepository.save(theList);
    }

    @Override
    public void removeElementFromOrder(List<Product> theListToRemove) {

    }

    public Date convertDate(LocalDate dateToConvert) {
        return java.sql.Date.valueOf(dateToConvert);
    }
}
