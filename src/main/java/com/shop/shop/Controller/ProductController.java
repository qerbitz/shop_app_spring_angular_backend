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

    int pierwsza = 0;
    int druga = 0;


    @PostMapping("/filter")
    public String filterpost(@RequestParam(value = "listOfCategoryChecked", required = false) List<Integer> listOfCategoryCheckedint,
                             @RequestParam(value = "listOfAgesChecked", required = false) List<String> listOfAgesChecked,
                             @RequestParam(value = "price_min", required = false) String price_min,
                             @RequestParam(value = "price_max", required = false) String price_max,
                             @RequestParam(required = false) String drop_category,
                             @RequestParam(required = false) String drop_age,
                             @RequestParam(value = "id_product", required = false) String id_product,
                             Model model) throws Exception {

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
        if (listOfAgesChecked == null && listOfCategoryCheckedint != null) {
            list = list.stream()
                    .filter(p -> listOfCategoryCheckedint.contains(p.getId_category().getId_category()))
                    .collect(Collectors.toList());
        }
        if (listOfAgesChecked != null && listOfCategoryCheckedint == null) {
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

        showCategoryLi(drop_category,drop_age, model);


        List<Integer> listRecommended = weka.Apriori(id_product);

       /* List<Product> listRecommendedProducts = new ArrayList<>();
        for (int i = 0; i < listRecommended.size(); i++) {
            listRecommendedProducts.add(productService.getProductById(listRecommended.get(i)));
        }

        model.addAttribute("recommendedList", listRecommendedProducts);*/

        return "product/products";
    }

    @GetMapping("/test")
        public String test(Model model) {
        List<Product> proponowaneNowe = new ArrayList<>();
        List<Product> proponowaneNowe2 = new ArrayList<>();

        Order order = orderService.getOrderById(26);

        Date actual_date = java.sql.Date.valueOf(LocalDate.now());

        long diff = Math.abs(actual_date.getTime() - order.getOrderDate().getTime());
        long diff_months = diff / (24 * 60 * 60 * 1000) / 30;
        long diff_years = diff / (24 * 60 * 60 * 1000) / 365;

        System.out.println("Roznica w miesiacach: " + diff_months);

        System.out.println("Roznica w latach: " + diff_years);

        String dawny_rozmiar = order.getCart().getCartItems().get(0).getProduct().getAge();
        String nowy_rozmiar = String.valueOf(diff_months);

        System.out.println(order.getCart().getCartItems().get(0).getProduct().getName());
        System.out.println("Rozmiar zamowionego produktu wczesniej: " + dawny_rozmiar);
        System.out.println("Ile dodac: " + nowy_rozmiar);

        int ile_dodac = Integer.parseInt(nowy_rozmiar);
        int nowy_poczatek = Integer.parseInt(dawny_rozmiar.substring(0,1)) + ile_dodac;
        int nowy_koniec = Integer.parseInt(dawny_rozmiar.substring(2,3)) + ile_dodac;

        System.out.println(nowy_poczatek+"-"+nowy_koniec);

        List<Product> proponowane = new ArrayList<>();
        proponowaneNowe = productService.getListOfProductsByAgeContaining(nowy_poczatek, nowy_koniec, order, proponowane);

        for(int i=0; i<proponowaneNowe.size(); i++){
            //System.out.println(proponowaneNowe.get(i).getAge().length());
            if(proponowaneNowe.get(i).getAge().length()<9){
                proponowaneNowe2.add(proponowaneNowe.get(i));
            }
        }

        model.addAttribute("productList", proponowaneNowe2);
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


        model.addAttribute("agesList", productService.getListOfAges());
        model.addAttribute("productList", productService.getListOfProducts());
        model.addAttribute("categoryList", categoryService.getListOfCategories());
        model.addAttribute("recommendedList", listRecommendedProducts);
        model.addAttribute("hidden_category",true);
        model.addAttribute("value_category", 0);

        model.addAttribute("hidden_age",true);
        model.addAttribute("value_age", 0);


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
        if (listOfAgesChecked == null && listOfCategoryChecked != null) {
            list = list.stream()
                    .filter(p -> listOfCategoryChecked.contains(p.getId_category().getId_category()))
                    .collect(Collectors.toList());
        }
        if (listOfAgesChecked != null && listOfCategoryChecked == null) {
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

    void showCategoryLi(String drop_category, String drop_age, Model model){
        int next_value_category=pierwsza;
        int next_value_age=druga;

        if(drop_category==null){
            next_value_category=pierwsza;
        }
        if(drop_age==null){
            next_value_age=druga;
        }

        if(drop_category!=null){
            next_value_category +=1;
        }
        if(drop_age!=null){
            next_value_age +=1;
        }

        if(next_value_category%2==0){
            model.addAttribute("hidden_category",true);
        }
        else if(next_value_category%2!=0)
        {
            model.addAttribute("hidden_category", false);
        }
        if(next_value_age%2==0){
            model.addAttribute("hidden_age", true);
        }
        else if(next_value_age%2!=0)
        {
            model.addAttribute("hidden_age", false);
        }

        model.addAttribute("value_category", next_value_category);
        model.addAttribute("value_age", next_value_age);

        pierwsza = next_value_category;
        druga = next_value_age;
    }
}
