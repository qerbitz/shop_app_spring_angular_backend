package com.shop.shop.Controller;

import com.shop.shop.Entity.Product;
import com.shop.shop.Service.Interface.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
