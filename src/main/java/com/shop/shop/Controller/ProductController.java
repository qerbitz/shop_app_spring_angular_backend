package com.shop.shop.Controller;

import com.shop.shop.Algorithm.Weka;
import com.shop.shop.Entity.Category;
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

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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


    @PostMapping("/test")
    public String test(@RequestParam(value = "listOfCategoryChecked", required = false) List<Integer> listOfCategoryChecked,
                       @RequestParam(value = "listOfAgesChecked", required = false) List<String> listOfAgesChecked,
                       Model model) throws ParseException {


        List<Product> proponowaneNowe = new ArrayList<>();


        model.addAttribute("categoryList", categoryService.getListOfCategories());
        model.addAttribute("agesList",productService.getListOfAges());
        //model.addAttribute("productList", productService.getListOfProducts());

        Order order = orderService.getOrderById(26);

        Date actual_date = java.sql.Date.valueOf(LocalDate.now());

        long diff = Math.abs(actual_date.getTime() - order.getOrderDate().getTime());
        long diff_months = diff / (24 * 60 * 60 * 1000) / 30;
        long diff_years = diff / (24 * 60 * 60 * 1000)/365;

        //System.out.println("Roznica w miesiacach: " + diff_months);

        //System.out.println("Roznica w latach: " +diff_years);

        String dawny_rozmiar = order.getCart().getCartItems().get(0).getProduct().getAge();
        String nowy_rozmiar = String.valueOf(diff_months);

        System.out.println(dawny_rozmiar);
        System.out.println(nowy_rozmiar);

        String nowy_rozmiar_2 = "";


        System.out.println(productService.getListOfProductsByAgeContaining("6-9").get(0).getName());

/*
        //Sprawdzanie roznicy daty od ostatniego zakupu do wieku dziecka
        if(diff_months>=2 && diff_months<=4){
            //proponowaneNowe = productService.getListOfProductsByAgeContaining("i");
        }
        if(diff_months>=4 && diff_months<=6){

        }
        if(diff_months>=6 && diff_months<=9){

        }
        if(diff_months>=9 && diff_months<=12){

        }
        if(diff_months>=12 && diff_months<=18){

        }
        if(diff_months>=18 && diff_months<=24){

        }
        if(diff_years>=1 && diff_years<=2){

        }
        if(diff_years>=2 && diff_years<=3){

        }
        if(diff_years>=3 && diff_years<=4){

        }
        if(diff_years>=4 && diff_years<=5){

        }
        if(diff_years>=5 && diff_years<=6){

        }
        if(diff_years>=6 && diff_years<=7){

        }
        if(diff_years>=7 && diff_years<=8){

        }*/

        List<Category> categoryCheckedList = new ArrayList<>();

        if(listOfCategoryChecked!=null){
            for(int i=0; i<listOfCategoryChecked.size(); i++){
                categoryCheckedList.add(categoryService.getCategoryById(listOfCategoryChecked.get(i)));
            }
        }

        List<Product> list = new ArrayList<>();
        

        if(listOfAgesChecked==null){
            list = productService.getListOfProducts().stream()
                    .filter(p -> listOfCategoryChecked.contains(p.getId_category().getId_category()))
                    .collect(Collectors.toList());
        }
        if(listOfCategoryChecked==null){
            list = productService.getListOfProducts().stream()
                    .filter(p -> listOfAgesChecked.contains(p.getAge()))
                    .collect(Collectors.toList());
        }
        else if(listOfAgesChecked!=null && listOfCategoryChecked!=null){
            list = productService.getListOfProducts().stream()
                    .filter(p -> listOfCategoryChecked.contains(p.getId_category().getId_category()))
                    .filter(p -> listOfAgesChecked.contains(p.getAge()))
                    .collect(Collectors.toList());
        }


        System.out.println(categoryCheckedList);
        System.out.println(listOfAgesChecked);

        model.addAttribute("categoryCheckedList", categoryCheckedList);
        model.addAttribute("agesCheckedList",listOfAgesChecked);
        model.addAttribute("productList", list);

        return "product/products";
    }

    @GetMapping("/filter/{criteria}")
    public String test2(Model model, @MatrixVariable(pathVar = "criteria") Map<String, List<String>> filterParams){

        model.addAttribute("productList",productService.findByCriteria(filterParams, productService.getListOfProducts()));

        //model.addAttribute("productList", productService.getListOfProducts());

        List<Category> listka = new ArrayList<>();
        List<String> listka2 = new ArrayList<>();

        model.addAttribute("categoryCheckedList",listka);
        model.addAttribute("agesCheckedList",listka2);
        model.addAttribute("categoryList", categoryService.getListOfCategories());
        model.addAttribute("agesList",productService.getListOfAges());

        return "product/products";
    }

    @RequestMapping("/productList")
    public String productList(@RequestParam(value = "id_product", required = false) String id_product, Model model) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        List<Integer> listRecommended = weka.Apriori(id_product);

        List<Product> listRecommendedProducts = new ArrayList<>();
        for (int i = 0; i < listRecommended.size(); i++) {
            listRecommendedProducts.add(productService.getProductById(listRecommended.get(i)));
        }


        model.addAttribute("productList", productService.getListOfProducts());
        model.addAttribute("categoryList", categoryService.getListOfCategories());
        model.addAttribute("recommendedList", listRecommendedProducts);


        if (authentication.getName().equals("anonymousUser")) {
            return "product/products";
        } else {
            User user = userService.getUserByUsername(authentication.getName());
            int cartId = user.getCart().getId_cart();
            model.addAttribute("quantity", cartService.getQuantityofCart(cartId));
            model.addAttribute("total", cartService.getTotalPrice(cartId));
            return "product/products";
        }

    }


    @PostMapping("/products_search")
    public String products_search(@RequestParam("value") String value, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        List<Category> categoryCheckedList = new ArrayList<>();

        model.addAttribute("productList", productService.getListOfProductsByName(value));
        model.addAttribute("categoryList", categoryService.getListOfCategories());
        model.addAttribute("categoryCheckedList", categoryCheckedList);


        if (authentication.getName().equals("anonymousUser")) {
            return "product/products";
        } else {
            User user = userService.getUserByUsername(authentication.getName());
            int cartId = user.getCart().getId_cart();
            model.addAttribute("quantity", cartService.getQuantityofCart(cartId));
            model.addAttribute("total", cartService.getTotalPrice(cartId));
            return "product/products";
        }

    }


    @GetMapping("/products_category")
    public String products_category(@RequestParam("values") int values, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByUsername(authentication.getName());
        int cartId = user.getCart().getId_cart();

        model.addAttribute("productList", productService.getListOfProductsByCategory(values));
        model.addAttribute("categoryList", categoryService.getListOfCategories());
        model.addAttribute("quantity", cartService.getQuantityofCart(cartId));

        return "product/products";
    }

    @PostMapping("/products_sort")
    public String products_sort(@RequestParam("option") int option, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByUsername(authentication.getName());
        int cartId = user.getCart().getId_cart();

        List<Product> productList = new ArrayList<>();

        switch (option) {
            case 1:
                productList = productService.getListOfProductOrderByPriceAsc();
                break;
            case 2:
                productList = productService.getListOfProductOrderByPriceDesc();
                break;
            case 3:
                productList = productService.getListOfProductsOrderByNameAsc();
                break;
            case 4:
                productList = productService.getListOfProductsOrderByNameDesc();
                break;
            case 5:
                productList = productService.getListOfProductsOrderBySaleDesc();
        }

        model.addAttribute("productList", productList);
        model.addAttribute("categoryList", categoryService.getListOfCategories());
        model.addAttribute("quantity", cartService.getQuantityofCart(cartId));
        return "product/products";
    }

    @GetMapping("/viewProduct/{productId}")
    public String viewProduct(@PathVariable int productId, Model model) {
        Product product = productService.getProductById(productId);
        model.addAttribute("product", product);

        return "product/viewProduct";
    }

}
