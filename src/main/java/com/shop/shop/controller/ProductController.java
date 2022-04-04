package com.shop.shop.controller;

import com.shop.shop.entity.*;
import com.shop.shop.repositories.ProductRepository;
import com.shop.shop.response.PurchaseResponse;
import com.shop.shop.service.Impl.EmailService;
import com.shop.shop.service.Interface.*;
import com.shop.shop.utility.JWTTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.mail.MessagingException;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/product")
public class ProductController {



    private ProductService productService;
    private ProductRepository productRepository;
    private CategoryService categoryService;
    private UserService userService;
    private EmailService emailService;
    private JWTTokenProvider jwtTokenProvider;

    @Autowired
    public ProductController(ProductService productService, ProductRepository productRepository, CategoryService categoryService, UserService userService, EmailService emailService, JWTTokenProvider jwtTokenProvider) {
        this.productService = productService;
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.userService = userService;
        this.emailService = emailService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

     //@Autowired
    //private AuthenticationManager authenticationManager;

    @GetMapping("/productList")
    public ResponseEntity<Page<Product>> productList(@RequestParam("page") int page,
                                                     @RequestParam("size") int size,
                                                     @RequestParam("sort_option") int sort_option){
        Pageable pageable = PageRequest.of(page,size);
        Page<Product> products = productService.allProductsList(pageable, sort_option);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/searchList")
    public ResponseEntity<Page<Product>> productSearchList(@RequestParam("page") int page,
                                                           @RequestParam("size") int size,
                                                           @RequestParam("theKeyword") String keyword){
        Pageable pageable = PageRequest.of(page,size);
        Page<Product> products = productService.ByNameContainingProductsList(pageable, keyword);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<Product>> productfilterList(@RequestParam("page") int page,
                                                             @RequestParam("size") int size,
                                                             @RequestParam("category") String category,
                                                             @RequestParam("gender") String gender,
                                                             @RequestParam(value = "price", required = false, defaultValue = "0") int price,
                                                             @RequestParam("sort_option") int sort_option){
        Pageable pageable = PageRequest.of(page,size);

        System.out.println(price);
        int kategoria = Integer.parseInt(category);
        Page<Product> products = productService.ByCategoryProductsList(pageable, kategoria, price, sort_option, gender);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/BySaleProductList")
    public ResponseEntity<List<Product>> BySaleProductList(){
        List<Product> productList = productService.BySaleProductsList();
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping("/categoryList")
    public ResponseEntity<List<Category>> categoryList(){
        List<Category> categories = categoryService.getListOfCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/viewProduct/{productId}")
    public ResponseEntity<Product> viewProduct(@PathVariable int productId) {

        Product product = productService.getProductById(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }


}
