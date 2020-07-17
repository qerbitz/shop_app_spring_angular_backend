package com.shop.shop.Controller;

import com.shop.shop.Entity.Product;
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

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        List<Product> productList = productService.getListOfProducts();
        model.addAttribute("productList", productList);
        return "products";
    }

    /*@GetMapping("/products")
    public String ProductsList(Model model){

        List<Product> productList = productService.getListOfProducts();
        model.addAttribute("productList", productList);

        return "products";
    }*/

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
            //List<Product>listOfDishes = productService.getDishesByIds(listToAdd);
           // productService.addOrder(listOfDishes);
        }
        return "products";
    }

    @PostMapping("/products_sort")
    public String listSortedProducts(@RequestParam("option") int option, Model model){

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
