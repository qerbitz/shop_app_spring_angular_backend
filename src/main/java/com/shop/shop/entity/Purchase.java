package com.shop.shop.entity;

import lombok.Data;

import java.util.Set;

@Data
public class Purchase {

    //private Address address;
    private String firstName;
    private String lastName;
    private String email;
    private Order order;
    private Set<OrderItem> orderItems;

}