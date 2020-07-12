package com.shop.shop.Controller;

import com.shop.shop.Entity.Product;
import com.shop.shop.Service.Interface.ProductService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class FirstPageController {


    @Autowired
    ProductService productService;

    @GetMapping("/products")
    public String ProductsList(Model model){

        List<Product> productList = productService.getListOfProducts();
        model.addAttribute("productList", productList);

        return "products";
    }

    @PostMapping("/products-search")
    public String listSearchedProducts(@RequestParam("value") String value, Model model){

        List<Product> productList = productService.getListOfProductsByName(value);
        model.addAttribute("productList", productList);

        return "products";
    }


    @GetMapping("/products-category")
    public String listSearchedProductsByCategory(@RequestParam("values") int values, Model model){

        List<Product> productList = productService.getListOfProductsByCategory(values);
        model.addAttribute("productList", productList);

        return "products";
    }

    @GetMapping("/add-to-cart")
    public String addToCart(@RequestParam(value = "listToAdd", required = false) List<Integer> listToAdd){

        List<Product> cart_shop = new ArrayList<>();
        if (listToAdd != null) {
            List<Product>listOfDishes = productService.getDishesByIds(listToAdd);
            productService.addOrder(listOfDishes);
        }
    }

}
