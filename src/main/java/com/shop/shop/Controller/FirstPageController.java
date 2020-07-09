package com.shop.shop.Controller;

import com.shop.shop.Entity.Product;
import com.shop.shop.Service.Interface.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

    @GetMapping("/products-search")
    public String ProductsListSearch(Model model){

        List<Product> productList = productService.getListOfProductsByName("n");
        model.addAttribute("productList", productList);

        return "products";
    }

    @GetMapping("/products-category")
    public String ProductsListByCategory(Model model){

        List<Product> productList = productService.getListOfProductsByCategory(2);
        model.addAttribute("productList", productList);

        return "products";
    }

}
