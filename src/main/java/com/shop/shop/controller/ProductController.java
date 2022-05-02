package com.shop.shop.controller;

import com.shop.shop.Dto.CategoryDto;
import com.shop.shop.Dto.ProductDto;
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

import static com.shop.shop.mapper.ReadDtoMapper.*;

@CrossOrigin(origins = "*")
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
    public ResponseEntity<Page<ProductDto>> productList(@RequestParam("page") int page,
                                                     @RequestParam("size") int size,
                                                     @RequestParam("sort_option") int sort_option){

        Pageable pageable = PageRequest.of(page,size);
        Page<Product> products = productService.allProductsList(pageable, sort_option);

        List<ProductDto> listka2 = mapProductToProductReadDtoList(productService.allProductsList(pageable, sort_option).getContent());


        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), listka2.size());
        final Page<ProductDto> page2 = new PageImpl<>(listka2.subList(start, end), pageable, listka2.size());


        return new ResponseEntity<>(page2, HttpStatus.OK);
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
                                                             @RequestParam("price") String price,
                                                             @RequestParam("sort_option") int sort_option){
        Pageable pageable = PageRequest.of(page,size);

        int kategoria = Integer.parseInt(category);
        String[] parts = price.split("-");
        String part1 = parts[0];
        String part2 = parts[1];

        int cena_min = Integer.parseInt(part1);
        int cena_max = Integer.parseInt(part2);


        System.out.println(kategoria);
        System.out.println(gender);
        System.out.println(cena_min);
        System.out.println(cena_max);


        Page<Product> products = productService.ByCategoryProductsList(pageable, kategoria, cena_min, cena_max, sort_option, gender);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/BySaleProductList")
    public ResponseEntity<List<Product>> BySaleProductList(){
        List<Product> productList = productService.BySaleProductsList();
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping("/categoryList")
    public ResponseEntity<List<CategoryDto>> categoryList(){
        List<Category> categories = categoryService.getListOfCategories();
        return new ResponseEntity<>(mapCategoryToPostReadDtoList(categories), HttpStatus.OK);
    }

    @GetMapping("/viewProduct/{productId}")
    public ResponseEntity<Product> viewProduct(@PathVariable int productId) {

        Product product = productService.getProductById(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

}
