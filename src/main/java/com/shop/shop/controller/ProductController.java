package com.shop.shop.controller;

import com.shop.shop.Dto.CategoryDto;
import com.shop.shop.Dto.ProductDto;
import com.shop.shop.entity.*;
import com.shop.shop.repositories.ProductRepository;
import com.shop.shop.service.Impl.EmailService;
import com.shop.shop.service.Interface.*;
import com.shop.shop.utility.JWTTokenProvider;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import java.util.List;


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

        List<ProductDto> finalList = mapProductToProductReadDtoList(productService.allProductsList(pageable, sort_option).getContent());

        return new ResponseEntity<>(convertToPage(pageable, finalList), HttpStatus.OK);
    }

   /* @GetMapping("/searchList")
    public ResponseEntity<Page<Product>> productSearchList(@RequestParam("page") int page,
                                                           @RequestParam("size") int size,
                                                           @RequestParam("theKeyword") String keyword){
        Pageable pageable = PageRequest.of(page,size);
        Page<Product> products = productService.ByNameContainingProductsList(pageable, keyword);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }*/

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
    public ResponseEntity<List<ProductDto>> BySaleProductList(){

        List<Product> productList = productService.BySaleProductsList();
        return new ResponseEntity<>(mapProductToProductReadDtoList(productList), HttpStatus.OK);
    }

    @GetMapping("/categoryList")
    public ResponseEntity<List<CategoryDto>> categoryList(){
        List<Category> categories = categoryService.getListOfCategories();
        return new ResponseEntity<>(mapCategoryToCategoryReadDtoList(categories), HttpStatus.OK);
    }

    @GetMapping("/viewProduct/{productId}")
    public ResponseEntity<ProductDto> viewProduct(@PathVariable int productId) throws NotFoundException {

        ProductDto product = productService.getProductById(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/test")
    public String test(){
        return "test";
    }

    public Page<ProductDto> convertToPage(Pageable pageable, List<ProductDto> productList){
        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), productList.size());
        final Page<ProductDto> page2 = new PageImpl<>(productList.subList(start, end), pageable, productList.size());

        return page2;
    }

}
