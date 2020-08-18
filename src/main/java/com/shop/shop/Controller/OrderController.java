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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping("/showUserDetails")
    public String showUserDetails(Model theModel) {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByUsername(authentication.getName());

        theModel.addAttribute("user", user);


        return "customerInfo";
    }

    @RequestMapping(value = "/createOrder", method = RequestMethod.POST)
    public String createOrder(@ModelAttribute("user") User user, Model theModel){

        userService.updateUser(user);

        Cart cart = cartService.getCartById(user.getCart().getId_cart());

        Order order = new Order();

        order.setCart(cart);
        order.setUser(user);
        order.setOrderDate(convertDate(LocalDate.now()));
        order.setStatus("Oczekujacy");


        theModel.addAttribute("order", order);
        theModel.addAttribute("user", user);
        theModel.addAttribute("cart", cartService.getCartById(user.getCart().getId_cart()).getCartItems());
        theModel.addAttribute("total",cartService.getTotalPrice(user.getCart().getId_cart()));


        return "orderConfirmation";
    }

    @RequestMapping(value = "/confirmOrder", method = RequestMethod.POST)
    public String confirmOrder(){


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByUsername(authentication.getName());
        Cart cart = cartService.getCartById(user.getCart().getId_cart());   

        Order order = new Order();
        order.setCart(cart);
        order.setUser(user);
        order.setOrderDate(convertDate(LocalDate.now()));
        order.setStatus("Oczekujacy");

        orderService.addNewOrder(order);
        Cart newCart = new Cart();
        user.setCart(newCart);
        cartService.addCart(newCart);
        userService.updateUser(user);

        return "redirect:/product/productList";
    }



    public Date convertDate(LocalDate dateToConvert) {
        return java.sql.Date.valueOf(dateToConvert);
    }
}
