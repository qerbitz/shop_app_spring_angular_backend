package com.shop.shop.Controller;

import com.shop.shop.Entity.Product;
import com.shop.shop.Service.Interface.CategoryService;
import com.shop.shop.Service.Interface.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {


    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;

    @RequestMapping("/productList")
    public String productList(Model model) {

        model.addAttribute("productList", productService.getListOfProducts());
        model.addAttribute("categoryList", categoryService.getListOfCategories());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return "products";
    }


    @PostMapping("/products_search")
    public String products_search(@RequestParam("value") String value, Model model){

        model.addAttribute("productList", productService.getListOfProductsByName(value));
        model.addAttribute("categoryList", categoryService.getListOfCategories());

        return "products";
    }


    @GetMapping("/products_category")
    public String products_category(@RequestParam("values") int values, Model model){

        model.addAttribute("productList", productService.getListOfProductsByCategory(values));
        model.addAttribute("categoryList", categoryService.getListOfCategories());

        return "products";
    }

    @PostMapping("/products_sort")
    public String products_sort(@RequestParam("option") int option, Model model){

        List<Product> productList = new ArrayList<>();

        switch(option){
            case 1: productList = productService.getListOfProductOrderByPriceAsc();
            break;
            case 2: productList = productService.getListOfProductOrderByPriceDesc();
            break;
            case 3: productList = productService.getListOfProductsOrderByNameAsc();
            break;
            case 4: productList = productService.getListOfProductsOrderByNameDesc();
        }

        model.addAttribute("productList", productList);
        model.addAttribute("categoryList", categoryService.getListOfCategories());
        return "products";
    }

    @RequestMapping("/viewProduct/{productId}")
    public String viewProduct(@PathVariable int productId, Model model) throws IOException {
        Product product = productService.getProductById(productId);
        model.addAttribute("product", product);

        return "viewProduct";
    }

}
