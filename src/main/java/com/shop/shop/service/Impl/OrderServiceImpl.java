package com.shop.shop.service.Impl;

import com.shop.shop.Dto.Purchase;
import com.shop.shop.entity.Address;
import com.shop.shop.entity.Order;
import com.shop.shop.entity.OrderItem;
import com.shop.shop.entity.User;
import com.shop.shop.repositories.OrderRepository;
import com.shop.shop.repositories.UserRepository;
import com.shop.shop.service.Interface.OrderService;
import com.shop.shop.service.Interface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Override
    public String addNewOrder(Purchase purchase) {
        

        Order order = purchase.getOrder();
        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);
        order.setOrderDate(convertDate(LocalDate.now()));
        order.setStatus("Oczekujący");

        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(item -> order.add(item));


        User user = userService.findUserByUsername(purchase.getUser().getUsername());
        user.setFirstName(purchase.getUser().getFirstName());
        user.setLastName(purchase.getUser().getLastName());
        user.setEmail(purchase.getUser().getEmail());

        Address address = user.getAddress();
        address.setCity(purchase.getAddress().getCity());
        address.setStreet(purchase.getAddress().getStreet());
        address.setZipCode(purchase.getAddress().getZipCode());

        order.setUser(user);


        orderRepository.save(order);
        userRepository.save(user);


        return orderTrackingNumber;
    }

    @Override
    public List<Order> getAllOrdersByUser(User user) {
        return orderRepository.getAllByUserOrderByOrderDateDesc(user);
    }

    @Override
    public Order getOrderById(String order_number) {
        return orderRepository.getOrderByOrderTrackingNumber(order_number);
    }

    public Date convertDate(LocalDate dateToConvert) {
        return java.sql.Date.valueOf(dateToConvert);
    }

    private String generateOrderTrackingNumber() {
        return UUID.randomUUID().toString();
    }
}
