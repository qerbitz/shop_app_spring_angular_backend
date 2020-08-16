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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();         //pobranie autentykacji

        Cart cart = cartService.getCartById(cartId);                                                    //pobranie obecnej karty

        Order order = new Order();

        User user = userService.getUserByUsername(authentication.getName());

        order.setCart(cart);
        order.setUser(user);
        order.setOrderDate(convertDate(LocalDate.now()));
        order.setStatus("Oczekujacy");


        Cart newCart = new Cart();                                                              //utworzenie nowej karty dla uzytklownika oraz przypisanie



        //orderService.addNewOrder(order);
        //user.setCart(newCart);
        //userService.saveUser(user);

        return "redirect:/order/showFormForUpdate";

    }


    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(Model theModel) {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByUsername(authentication.getName());

        theModel.addAttribute("user", user);


        return "customerInfo";
    }

    public Date convertDate(LocalDate dateToConvert) {
        return java.sql.Date.valueOf(dateToConvert);
    }
}
