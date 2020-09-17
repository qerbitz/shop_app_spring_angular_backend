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

import java.io.*;
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


        return "order/customerInfo";
    }

    @PostMapping("/createOrder")
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


        return "order/orderConfirmation";
    }

    @PostMapping("/confirmOrder")
    public String confirmOrder() throws IOException {

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

        appendToApriori(order);


        return "redirect:/product/productList";
    }

    @GetMapping("/allOrders")
    public String allOrders(Model theModel){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByUsername(authentication.getName());

        theModel.addAttribute("orders_list",orderService.getAllOrdersByUser(userService.getUserByUsername(user.getUsername())));
        return "order/ordersList";
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

        return "order/orderDetails";
    }

    public Date convertDate(LocalDate dateToConvert) {
        return java.sql.Date.valueOf(dateToConvert);
    }

    public void appendToApriori(Order order) throws IOException {

        BufferedReader input = new BufferedReader(new FileReader("src/main/resources/Apriori.arff"));

        String word = ""; //nowy przypadek do dodania
        String last = ""; //ostatni przypadek
        String line; //aktualna linia w pliku Apriori.arff

        while ((line = input.readLine()) != null) {  //Szukamy ostatniego przypadku w celu sprawdzenia poprawnej dlugosci
            last = line;
        }

        for(CartItem product : order.getCart().getCartItems()){  //Zapisywanie najnowszego zamowienia do zmiennej typu String w celu zapisania nowego przypadku reguł
                word = word + product.getProduct().getId_product() + ", ";
        }

        while(last.length()!=word.length()-2){  //Dopasowanie dlugosci nowego zamowienia do dlugosci wszystkich reguł asocjacyjnych
            word = word + "?, ";
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
