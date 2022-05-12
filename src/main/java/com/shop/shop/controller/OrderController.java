package com.shop.shop.controller;

import com.shop.shop.Dto.OrderProductsDto;
import com.shop.shop.Dto.OrderDto;
import com.shop.shop.Dto.Purchase;
import com.shop.shop.entity.Order;
import com.shop.shop.entity.User;
import com.shop.shop.service.Interface.OrderService;
import com.shop.shop.service.Interface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.shop.shop.mapper.ReadDtoMapper.*;

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
    public ResponseEntity<Order> saveOrder(@RequestBody Purchase purchase){

        orderService.addNewOrder(purchase);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/allOrders")
    public ResponseEntity<List<OrderDto>> allOrders(){

        User user = userService.findUserByUsername("test2503");
        List<Order> all_user_orders = orderService.getAllOrdersByUser(user);


        return new ResponseEntity<>(mapOrderToOrderListReadDtoList(all_user_orders), HttpStatus.OK);
    }

    @GetMapping("/orderDetails")
    public ResponseEntity<OrderDto> orderDetails(@RequestParam("order_id") String order_id){

        Order order = orderService.getOrderById(order_id);

        return new ResponseEntity<>(testowe1(order), HttpStatus.OK);
    }
}
