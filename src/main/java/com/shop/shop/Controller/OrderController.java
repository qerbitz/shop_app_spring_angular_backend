package com.shop.shop.Controller;

import com.shop.shop.Entity.Cart;
import com.shop.shop.Entity.Order;
import com.shop.shop.Entity.User;
import com.shop.shop.Service.Interface.CartService;
import com.shop.shop.Service.Interface.OrderService;
import com.shop.shop.Service.Interface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.Date;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    CartService cartService;

    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;

    @RequestMapping("/newOrder/{cartId}")
    public String createOrder(@PathVariable("cartId") int cartId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Cart cart = cartService.getCartById(cartId);

        Order order = new Order();
        order.setCart(cart);
        order.setUser(userService.getUserByUsername(authentication.getName()));
        order.setOrderDate(convertDate(LocalDate.now()));
        order.setStatus("Oczekujacy");

        orderService.addNewOrder(order);

        //return "redirect:/checkout?cartId="+cartId;
        return "redirect:/cart/"+cartId;

    }

    public Date convertDate(LocalDate dateToConvert) {
        return java.sql.Date.valueOf(dateToConvert);
    }
}
