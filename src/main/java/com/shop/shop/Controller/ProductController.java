package com.shop.shop.Controller;

import com.shop.shop.Algorithm.Weka;
import com.shop.shop.Entity.*;
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


    @PostMapping("/filter")
    public String filterpost(@RequestParam(value = "listOfCategoryChecked", required = false) List<Integer> listOfCategoryCheckedint,
                       @RequestParam(value = "listOfAgesChecked", required = false) List<String> listOfAgesChecked,
                       @RequestParam(value = "price_min", required = false) String price_min,
                       @RequestParam(value = "price_max", required = false) String price_max,
                       Model model) throws ParseException {

        List<Product> proponowaneNowe = new ArrayList<>();

        Order order = orderService.getOrderById(26);

        Date actual_date = java.sql.Date.valueOf(LocalDate.now());

        long diff = Math.abs(actual_date.getTime() - order.getOrderDate().getTime());
        long diff_months = diff / (24 * 60 * 60 * 1000) / 30;
        long diff_years = diff / (24 * 60 * 60 * 1000) / 365;

        System.out.println("Roznica w miesiacach: " + diff_months);

        System.out.println("Roznica w latach: " +diff_years);

        String dawny_rozmiar = order.getCart().getCartItems().get(0).getProduct().getAge();
        String nowy_rozmiar = String.valueOf(diff_months);

         System.out.println(dawny_rozmiar);
         System.out.println(nowy_rozmiar);


        List<Category> categoryCheckedList = new ArrayList<>();


        if (listOfCategoryCheckedint != null) {
            for (int i = 0; i < listOfCategoryCheckedint.size(); i++) {
                categoryCheckedList.add(categoryService.getCategoryById(listOfCategoryCheckedint.get(i)));
            }
        }

        List<Product> list = productService.getListOfProducts();


        //Sortowania poprzez cene
        if (price_min.compareTo("") == 0 && price_max.compareTo("") != 0) { //jezeli podana jest tylko maxymalna kwota
            int price_max_parsed = Integer.parseInt(price_max);
            list = productService.getListOfProductByPriceBetween(0, price_max_parsed);
        }
        if (price_min.compareTo("") != 0 && price_max.compareTo("") == 0) { //jezeli podana jest tylko minimalna kwota
            int price_min_parsed = Integer.parseInt(price_min);
            list = productService.getListOfProductByPriceBetween(price_min_parsed, 9999999);
        }
        if (price_min.compareTo("") != 0 && price_max.compareTo("") != 0) { //Jezeli podane sa obydwie kwoty

            int price_min_parsed = Integer.parseInt(price_min);
            int price_max_parsed = Integer.parseInt(price_max);
            list = productService.getListOfProductByPriceBetween(price_min_parsed, price_max_parsed);
        }

        //Sortowania poprzez kategorie oraz przeznaczenie wiekowe
        if (listOfAgesChecked == null && listOfCategoryCheckedint !=null) {
            list = list.stream()
                    .filter(p -> listOfCategoryCheckedint.contains(p.getId_category().getId_category()))
                    .collect(Collectors.toList());
        }
        if (listOfAgesChecked !=null && listOfCategoryCheckedint == null) {
            list = list.stream()
                    .filter(p -> listOfAgesChecked.contains(p.getAge()))
                    .collect(Collectors.toList());
        } else if (listOfAgesChecked != null && listOfCategoryCheckedint != null) {
            list = list.stream()
                    .filter(p -> listOfCategoryCheckedint.contains(p.getId_category().getId_category()))
                    .filter(p -> listOfAgesChecked.contains(p.getAge()))
                    .collect(Collectors.toList());
        }


        model.addAttribute("categoryList", categoryService.getListOfCategories());
        model.addAttribute("agesList", productService.getListOfAges());
        model.addAttribute("categoryCheckedList", categoryCheckedList);
        model.addAttribute("categoryCheckedListint", listOfCategoryCheckedint);
        model.addAttribute("agesCheckedList", listOfAgesChecked);
        model.addAttribute("productList", list);
        model.addAttribute("price_min", price_min);
        model.addAttribute("price_max", price_max);

        return "product/products";
    }

    @RequestMapping("/nooo")
    public String jazda(Model model){


        model.addAttribute("cart", cartService.getCartById(21).getCartItems());
        return "shopping-detail";
    }

    @RequestMapping("/productList")
    public String productList(@RequestParam(value = "id_product", required = false) String id_product, Model model) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        List<Integer> listRecommended = weka.Apriori(id_product);

        List<Product> listRecommendedProducts = new ArrayList<>();
        for (int i = 0; i < listRecommended.size(); i++) {
            listRecommendedProducts.add(productService.getProductById(listRecommended.get(i)));
        }


        model.addAttribute("agesList", productService.getListOfAges());
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

    @PostMapping("/products_sort")
    public String products_sort(@RequestParam("option") int option, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

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

    @GetMapping("/viewProduct/{productId}")
    public String viewProduct(@PathVariable int productId, Model model) {
        Product product = productService.getProductById(productId);
        model.addAttribute("product", product);

        return "product/viewProduct";
    }

    @GetMapping("/filter")
    public String filterget(@RequestParam(value = "categoryCheckedList", required = false) List<Integer> listOfCategoryChecked,
                       @RequestParam(value = "listOfAgesChecked", required = false) List<String> listOfAgesChecked,
                       @RequestParam(value = "price_min", required = false) String price_min,
                       @RequestParam(value = "price_max", required = false) String price_max,
                       Model model) throws ParseException {


        List<Category> categoryCheckedList = new ArrayList<>();


        if (listOfCategoryChecked != null) {
            for (int i = 0; i < listOfCategoryChecked.size(); i++) {
                categoryCheckedList.add(categoryService.getCategoryById(listOfCategoryChecked.get(i)));
            }
        }

        List<Product> list = productService.getListOfProducts();


        //Sortowania poprzez cene
        if (price_min.compareTo("") == 0 && price_max.compareTo("") != 0) { //jezeli podana jest tylko maxymalna kwota
            int price_max_parsed = Integer.parseInt(price_max);
            list = productService.getListOfProductByPriceBetween(0, price_max_parsed);
        }
        if (price_min.compareTo("") != 0 && price_max.compareTo("") == 0) { //jezeli podana jest tylko minimalna kwota
            int price_min_parsed = Integer.parseInt(price_min);
            list = productService.getListOfProductByPriceBetween(price_min_parsed, 9999999);
        }
        if (price_min.compareTo("") != 0 && price_max.compareTo("") != 0) { //Jezeli podane sa obydwie kwoty

            int price_min_parsed = Integer.parseInt(price_min);
            int price_max_parsed = Integer.parseInt(price_max);
            list = productService.getListOfProductByPriceBetween(price_min_parsed, price_max_parsed);
        }

        //Sortowania poprzez kategorie oraz przeznaczenie wiekowe
        if (listOfAgesChecked == null && listOfCategoryChecked !=null) {
            list = list.stream()
                    .filter(p -> listOfCategoryChecked.contains(p.getId_category().getId_category()))
                    .collect(Collectors.toList());
        }
        if (listOfAgesChecked !=null && listOfCategoryChecked == null) {
            list = list.stream()
                    .filter(p -> listOfAgesChecked.contains(p.getAge()))
                    .collect(Collectors.toList());
        } else if (listOfAgesChecked != null && listOfCategoryChecked != null) {
            list = list.stream()
                    .filter(p -> listOfCategoryChecked.contains(p.getId_category().getId_category()))
                    .filter(p -> listOfAgesChecked.contains(p.getAge()))
                    .collect(Collectors.toList());
        }


        model.addAttribute("categoryList", categoryService.getListOfCategories());
        model.addAttribute("agesList", productService.getListOfAges());
        model.addAttribute("categoryCheckedList", categoryCheckedList);
        model.addAttribute("agesCheckedList", listOfAgesChecked);
        model.addAttribute("productList", list);
        model.addAttribute("price_min", price_min);
        model.addAttribute("price_max", price_max);

        return "product/products";
    }
}
