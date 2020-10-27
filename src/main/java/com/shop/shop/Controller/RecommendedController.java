package com.shop.shop.Controller;

import com.shop.shop.Entity.Order;
import com.shop.shop.Entity.Product;
import com.shop.shop.Service.Interface.OrderService;
import com.shop.shop.Service.Interface.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/product")
public class RecommendedController {

    @Autowired
    OrderService orderService;

    @Autowired
    ProductService productService;

    @RequestMapping("/test")
    public String test(Model model) {
        List<Product> recommendedNewList = new ArrayList<>();

        Order order = orderService.getOrderById(3);

        for(int i=0; i<order.getCart().getCartItems().size(); i++){
            System.out.println("Produkt: "+order.getCart().getCartItems().get(i).getProduct().getName()+" ,Rozmiar: "+order.getCart().getCartItems().get(i).getProduct().getSize_age().getProduct_size()+" ,Wiek: "+order.getCart().getCartItems().get(i).getProduct().getSize_age().getProduct_age());
        }

        Date actual_date = java.sql.Date.valueOf(LocalDate.now());

        long diff = Math.abs(actual_date.getTime() - order.getOrderDate().getTime());
        long diff_months = diff / (24 * 60 * 60 * 1000) / 30;

        System.out.println("Roznica w miesiacach: " + diff_months);



        List<Product> proponowane = new ArrayList<>();
        for(int i=0; i<order.getCart().getCartItems().size(); i++){
            String dawny_rozmiar = order.getCart().getCartItems().get(i).getProduct().getSize_age().getProduct_age();
            String nowy_rozmiar = String.valueOf(diff_months);

            int ile_dodac = Integer.parseInt(nowy_rozmiar);
            int new_beggining = Integer.parseInt(dawny_rozmiar.substring(0,1)) + ile_dodac;
            int new_end = Integer.parseInt(dawny_rozmiar.substring(2,3)) + ile_dodac;

            System.out.println("Nowy szukany przedział wiekowy: "+new_beggining+"-"+new_end+" msc");

            recommendedNewList = productService.getListOfProductsByAgeContaining(new_beggining, new_end, proponowane);
        }


        List<Product> filteredlist = new ArrayList<>();
        for(int j=0; j<order.getCart().getCartItems().size(); j++){
            List<Product> filteredlist2 = new ArrayList<>();
            int finalJ = j;
            filteredlist2 = recommendedNewList.stream()
                    .filter(p -> p.getId_category().getName() == order.getCart().getCartItems().get(finalJ).getProduct().getId_category().getName())
                    .collect(Collectors.toList());
            filteredlist.addAll(filteredlist2);
        }


        for (int i=0; i<filteredlist.size(); i++){
            System.out.println(filteredlist.get(i).getName()+ " " +filteredlist.get(i).getSize_age().getProduct_age()+ " "+filteredlist.get(i).getSize_age().getId_size_age());
        }

        return "index";

    }
}
