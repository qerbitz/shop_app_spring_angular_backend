package com.shop.shop.controller;

import com.shop.shop.entity.Order;
import com.shop.shop.entity.OrderItem;
import com.shop.shop.entity.Product;
import com.shop.shop.entity.User;
import com.shop.shop.service.Interface.OrderService;
import com.shop.shop.service.Interface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/order")
public class OrderController {



    private UserService userService;
    private OrderService orderService;
    private AuthenticationManager authenticationManager;

    @Autowired
    public OrderController(UserService userService, AuthenticationManager authenticationManager, OrderService orderService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.orderService = orderService;
    }


    @PostMapping("/placeOrder")
    public ResponseEntity<Order> saveOrder(@RequestBody Order order){
        System.out.println(order.getUser());
        order.setUser(userService.findUserByUsername("test2503"));
        orderService.addNewOrder(order);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/allOrders")
    public ResponseEntity<List<Order>> allOrders(){

        User user = userService.findUserByUsername("test2503");
        List<Order> all_user_orders = orderService.getAllOrdersByUser(user);

        System.out.println(all_user_orders.get(0).getOrderDate());

        return new ResponseEntity<>(all_user_orders, HttpStatus.OK);
    }

    @GetMapping("/orderDetails")
    public ResponseEntity<Order> orderDetails(@RequestParam("order_id") String order_id){

        Order order = orderService.getOrderById(order_id);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
