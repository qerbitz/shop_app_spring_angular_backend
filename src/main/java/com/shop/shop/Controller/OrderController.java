package com.shop.shop.Controller;

import com.shop.shop.Entity.Product;
import com.shop.shop.Service.Interface.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

import java.util.Date;
import java.time.LocalDate;

@Controller
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("/shopping_cart")
    public String shopping_cart(Model model){


        List<Product> productList = new ArrayList<>();
        orderService.addOrder(productList);
        model.addAttribute("productList", productList);

        return "shopping-detail";
    }

}
