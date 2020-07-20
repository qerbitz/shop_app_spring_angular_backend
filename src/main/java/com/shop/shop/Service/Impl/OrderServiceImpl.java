package com.shop.shop.Service.Impl;

import com.shop.shop.Entity.Order;
import com.shop.shop.Entity.OrderDetail;
import com.shop.shop.Entity.Product;
import com.shop.shop.Entity.User;
import com.shop.shop.Repositories.OrderDetailRepository;
import com.shop.shop.Repositories.OrderRepository;
import com.shop.shop.Repositories.ProductRepository;
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
    OrderDetailRepository orderDetailRepository;
    ProductRepository productRepository;

    @Override
    public void addOrder(Order order, OrderDetail orderDetail, User user) {

        order.setOrderDate(convertDate(LocalDate.now()));
        order.setId_user(user);
        order.setStatus("Oczekujacy");
        order.setId_order_detail(orderDetail);
        orderRepository.save(order);
    }

    @Override
    public void addOrderDetail(Product product, int quantity) {


        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProduct(product);
        orderDetail.setQuanity(quantity);
        orderDetail.setAmount(quantity*product.getPrice());


        product.setQuantity(product.getQuantity()-quantity);

        orderDetailRepository.save(orderDetail);
        productRepository.save(product);
    }

    @Override
    public List<OrderDetail> getCartByUserId(int userId) {
        return null;
    }

    @Override
    public void updateQuantityByCartId(int cartId, int quantity, double price) {

    }

    @Override
    public List<OrderDetail> removeCartByUserId(int cartId, int userId) {
        return null;
    }

    @Override
    public List<OrderDetail> removeAllCartByUserId(int userId) {
        return null;
    }

    @Override
    public List<OrderDetail> cart_list() {
        return orderDetailRepository.findAllByUser();
    }


    public Date convertDate(LocalDate dateToConvert) {
        return java.sql.Date.valueOf(dateToConvert);
    }
}
