package com.shop.shop.Dto;

import com.shop.shop.entity.Address;
import com.shop.shop.entity.Order;
import com.shop.shop.entity.OrderItem;
import com.shop.shop.entity.User;
import lombok.Data;

import java.util.Set;

@Data
public class Purchase {

    private User user;
    private Address address;
    private Order order;
    private Set<OrderItem> orderItems;

}