package com.shop.shop.Controller;

import com.shop.shop.Algorithm.Weka;
import com.shop.shop.Entity.CartItem;
import com.shop.shop.Entity.Order;
import com.shop.shop.Entity.Product;
import com.shop.shop.Entity.User;
import com.shop.shop.Service.Interface.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {


    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    UserService userService;
    @Autowired
    CartService cartService;

    @Autowired
    OrderService orderService;

    Weka weka = new Weka();


    @RequestMapping("/test")
    public String test(Model model) throws Exception {

        model.addAttribute("categoryList", categoryService.getListOfCategories());
        model.addAttribute("productList", productService.getListOfProducts());
        List<Integer> listRecommended = weka.Apriori("4");

        List<Product> listRecommendedProducts = new ArrayList<>();
        for(int i=0; i<listRecommended.size(); i++){
            listRecommendedProducts.add(productService.getProductById(i+1));
        }
        System.out.println(listRecommended);
        System.out.println(listRecommendedProducts.get(0).getName());
        System.out.println(listRecommendedProducts.get(1).getName());

        return "index";
    }


    @RequestMapping("/productList")
    public String productList(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByUsername(authentication.getName());
        int cartId = user.getCart().getId_cart();

        model.addAttribute("productList", productService.getListOfProducts());
        model.addAttribute("categoryList", categoryService.getListOfCategories());
        model.addAttribute("quantity", cartService.getQuantityofCart(cartId));
        model.addAttribute("total", cartService.getTotalPrice(cartId));

        return "product/products";
    }


    @PostMapping("/products_search")
    public String products_search(@RequestParam("value") String value, Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByUsername(authentication.getName());
        int cartId = user.getCart().getId_cart();

        model.addAttribute("productList", productService.getListOfProductsByName(value));
        model.addAttribute("categoryList", categoryService.getListOfCategories());
        model.addAttribute("quantity", cartService.getQuantityofCart(cartId));

        return "product/products";
    }


    @GetMapping("/products_category")
    public String products_category(@RequestParam("values") int values, Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByUsername(authentication.getName());
        int cartId = user.getCart().getId_cart();

        model.addAttribute("productList", productService.getListOfProductsByCategory(values));
        model.addAttribute("categoryList", categoryService.getListOfCategories());
        model.addAttribute("quantity", cartService.getQuantityofCart(cartId));

        return "product/products";
    }

    @PostMapping("/products_sort")
    public String products_sort(@RequestParam("option") int option, Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByUsername(authentication.getName());
        int cartId = user.getCart().getId_cart();

        List<Product> productList = new ArrayList<>();

        switch(option){
            case 1: productList = productService.getListOfProductOrderByPriceAsc();
            break;
            case 2: productList = productService.getListOfProductOrderByPriceDesc();
            break;
            case 3: productList = productService.getListOfProductsOrderByNameAsc();
            break;
            case 4: productList = productService.getListOfProductsOrderByNameDesc();
            break;
            case 5: productList = productService.getListOfProductsOrderBySaleDesc();
        }

        model.addAttribute("productList", productList);
        model.addAttribute("categoryList", categoryService.getListOfCategories());
        model.addAttribute("quantity", cartService.getQuantityofCart(cartId));
        return "product/products";
    }

    @GetMapping("/viewProduct/{productId}")
    public String viewProduct(@PathVariable int productId, Model model) throws IOException {
        Product product = productService.getProductById(productId);
        model.addAttribute("product", product);

        return "product/viewProduct";
    }

}
