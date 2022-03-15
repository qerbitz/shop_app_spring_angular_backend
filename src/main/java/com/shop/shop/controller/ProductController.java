package com.shop.shop.controller;

import com.shop.shop.entity.*;
import com.shop.shop.repositories.ProductRepository;
import com.shop.shop.response.PurchaseResponse;
import com.shop.shop.service.Interface.*;
import com.shop.shop.utility.JWTTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;


import javax.mail.MessagingException;
import java.util.List;


@CrossOrigin(origins = "https://spring-angular-frontend-shop.herokuapp.com/")
@RestController
@RequestMapping("/product")
public class ProductController {


    @Autowired
    ProductService productService;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryService categoryService;
    @Autowired
    UserService userService;
    @Autowired
    CheckoutService checkoutService;
    @Autowired
    EmailService emailService;

    @Autowired
    JWTTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/productList")
    public ResponseEntity<Page<Product>> productList(@RequestParam("page") int page, @RequestParam("size") int size){
        Page<Product> products = productService.listAll(page, size);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/searchList")
    public ResponseEntity<Page<Product>> productSearchList(@RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("theKeyword") String keyword){

        Pageable pageable = PageRequest.of(page,size);
        Page<Product> products = productRepository.findByNameContaining(pageable, keyword);
        return new ResponseEntity<>(products, HttpStatus.OK);
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


    @PostMapping("/checkout")
    public PurchaseResponse placeOrder(@RequestBody Purchase purchase) throws MessagingException {
        PurchaseResponse purchaseResponse = checkoutService.placeOrder(purchase);

        //emailService.sendTestEmail(purchase);
        return purchaseResponse;
    }

    @GetMapping("/test")
    public String testowa(){
        return "test";
    }

}
