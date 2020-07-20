package com.shop.shop.Controller;

import com.shop.shop.Entity.Category;
import com.shop.shop.Entity.Product;
import com.shop.shop.Service.Interface.CategoryService;
import com.shop.shop.Service.Interface.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "product")
public class FirstPageController {


    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {

        List<Product> productList = productService.getListOfProducts();
        List<Category> categoryList = categoryService.getListOfCategories();

        model.addAttribute("productList", productList);
        model.addAttribute("categoryList", categoryList);

        return "products";
    }

    /*@GetMapping("/products")
    public String ProductsList(Model model){

        List<Product> productList = productService.getListOfProducts();
        model.addAttribute("productList", productList);

        return "products";
    }*/

    @PostMapping("/products_search")
    public String products_search(@RequestParam("value") String value, Model model){

        List<Product> productList = productService.getListOfProductsByName(value);
        model.addAttribute("productList", productList);

        return "products";
    }


    @GetMapping("/products_category")
    public String products_category(@RequestParam("values") int values, Model model){

        List<Product> productList = productService.getListOfProductsByCategory(values);
        model.addAttribute("productList", productList);

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
        return "products";
    }

}
