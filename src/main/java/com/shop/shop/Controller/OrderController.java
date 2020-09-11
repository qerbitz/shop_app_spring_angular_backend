package com.shop.shop.Controller;

import com.shop.shop.Entity.Cart;
import com.shop.shop.Entity.CartItem;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        //User user = userService.getUserByUsername(authentication.getName());
        User user = userService.getUserByUsername("najnowszy");

        theModel.addAttribute("user", user);


        return "customerInfo";
    }

    @PostMapping("/createOrder")
    public String createOrder(@ModelAttribute("user") User user, Model theModel, RedirectAttributes redirectAttributes){

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

    @PostMapping("/confirmOrder")
    public String confirmOrder(@ModelAttribute("order2") Order order) {

        System.out.println(order.toString());


       /* Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
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
        userService.updateUser(user);*/

        //appendToApriori(order);


        return "redirect:/product/productList";
    }

    @GetMapping("/allOrders")
    public String allOrders(Model theModel){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        theModel.addAttribute("orders_list",orderService.getAllOrdersByUser(userService.getUserByUsername("najnowszy")));
        return "ordersList";
    }

    @GetMapping("/detailsOrder/{cartId}")
    public String detailsOrder(@PathVariable(value = "cartId") int cartId, @RequestParam(value = "orderId") int orderId, Model theModel, RedirectAttributes redirectAttributes){


        if(cartId!=orderService.getOrderById(orderId).getCart().getId_cart())
        {
            redirectAttributes.addAttribute("cartId", orderService.getOrderById(orderId).getCart().getId_cart());
            redirectAttributes.addAttribute("orderId", orderId);
            return "redirect:/order/detailsOrder/{cartId}";
        }



        theModel.addAttribute("cart", cartService.getCartById(cartId).getCartItems());

        return "orderDetails";
    }



    public Date convertDate(LocalDate dateToConvert) {
        return java.sql.Date.valueOf(dateToConvert);
    }

    public void appendToApriori(Order order){

        String word ="";
        for(CartItem product : order.getCart().getCartItems()){
                word = word + product.getProduct().getId_product() + ", ";
        }

        Writer output = null;
        try {
            output = new BufferedWriter(new FileWriter("src/main/resources/Apriori.arff", true));
            output.append("\n");
            output.append(word.substring(0,word.length()-2));
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
