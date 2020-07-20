package com.shop.shop.Service.Interface;

import com.shop.shop.Entity.Order;
import com.shop.shop.Entity.OrderDetail;
import com.shop.shop.Entity.Product;
import com.shop.shop.Entity.User;

import java.util.List;

public interface OrderService {

    List<OrderDetail> cart_list();

    void addOrder(Order order, OrderDetail orderDetail, User user);

    void addOrderDetail(Product product, int quantity);

    List<OrderDetail> getCartByUserId(int userId);

    void updateQuantityByCartId(int cartId,int quantity,double price);

    List<OrderDetail> removeCartByUserId(int cartId,int userId);

    List<OrderDetail> removeAllCartByUserId(int userId);
}
