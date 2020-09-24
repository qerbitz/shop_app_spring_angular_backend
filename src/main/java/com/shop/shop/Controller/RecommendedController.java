package com.shop.shop.Controller;

import com.shop.shop.Entity.Order;
import com.shop.shop.Service.Interface.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;

@Controller
@RequestMapping("/product")
public class RecommendedController {

    @Autowired
    OrderService orderService;

    public String test(Model model) throws ParseException {


        //model.addAttribute("categoryList", categoryService.getListOfCategories());

        Order order = orderService.getOrderById(24);
        System.out.println(order.getCart().getCartItems().get(0).getProduct().getId_category().getName());
        System.out.println("Data zamowienia: "+order.getOrderDate());
        System.out.println("Aktualna data: "+ LocalDate.now());




        Date actual_date = java.sql.Date.valueOf(LocalDate.now());

        long diff = Math.abs(actual_date.getTime()-order.getOrderDate().getTime());
        long diff_days = diff/ (24 * 60 * 60 * 1000);

        System.out.println("Roznica w dniach: "+diff_days);

        System.out.println(diff_days/30);



        //model.addAttribute("categoryList", categoryService.getListOfCategories());

        return "product/products";
    }
}
