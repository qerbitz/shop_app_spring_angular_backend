package com.shop.shop.Controller;

import com.shop.shop.Algorithm.Weka;
import com.shop.shop.Entity.*;
import com.shop.shop.Repositories.ProductRepository;
import com.shop.shop.Service.Interface.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    ProductRepository productRepository;
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
    int trzecia = 0;
    int czwarta = 0;


    @PostMapping("/filter")
    public String filterpost(@RequestParam(value = "listOfCategoryChecked", required = false) List<Integer> listOfCategoryCheckedint,
                             @RequestParam(value = "listOfAgesChecked", required = false) List<String> listOfAgesChecked,
                             @RequestParam(value = "listOfSeasonChecked", required = false) List<String> listOfSeasonChecked,
                             @RequestParam(value = "listOfGenderChecked", required = false) List<String> listOfGenderChecked,
                             @RequestParam(value = "price_min", required = false) String price_min,
                             @RequestParam(value = "price_max", required = false) String price_max,
                             @RequestParam(required = false) String drop_category,
                             @RequestParam(required = false) String drop_age,
                             @RequestParam(required = false) String drop_gender,
                             @RequestParam(required = false) String drop_season,
                             @RequestParam(value = "id_product", required = false) String id_product,
                             Model model) throws Exception {

        List<Category> categoryCheckedList = new ArrayList<>();


        if (listOfCategoryCheckedint != null) {
            for (int i = 0; i < listOfCategoryCheckedint.size(); i++) {
                categoryCheckedList.add(categoryService.getCategoryById(listOfCategoryCheckedint.get(i)));
            }
        }

        List<Product> list = productService.getListOfProducts();

        list = filtering(listOfCategoryCheckedint, listOfAgesChecked, listOfSeasonChecked, listOfGenderChecked, list, price_min, price_max, drop_category, drop_age, drop_gender, drop_season);


        model.addAttribute("categoryList", categoryService.getListOfCategories());
        model.addAttribute("agesList", productService.getListOfAges());
        model.addAttribute("genderList", productService.getListOfGenders());
        model.addAttribute("seasonList", productService.getListOfSeasons());

        model.addAttribute("categoryCheckedList", categoryCheckedList);
        model.addAttribute("categoryCheckedListint", listOfCategoryCheckedint);
        model.addAttribute("agesCheckedList", listOfAgesChecked);
        model.addAttribute("genderCheckedList", listOfGenderChecked);
        model.addAttribute("seasonCheckedList", listOfSeasonChecked);

        model.addAttribute("productList", list);
        model.addAttribute("price_min", price_min);
        model.addAttribute("price_max", price_max);

        showCategoryLi(drop_category, drop_age, drop_gender, drop_season, model);

        return "product/products";
    }


    @RequestMapping("/productList")
    public String productList(@RequestParam(value = "id_product", required = false) String id_product, Model model) throws Exception {

        pierwsza=0;
        druga=0;
        trzecia=0;
        czwarta=0;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        List<Integer> listRecommended = weka.Apriori(id_product);

        List<Product> listRecommendedProducts = new ArrayList<>();
        if (listRecommended != null) {
            for (int i = 0; i < listRecommended.size(); i++) {
                listRecommendedProducts.add(productService.getProductById(listRecommended.get(i)));
            }
        }

        model.addAttribute("productList", productService.getListOfProducts());

        model.addAttribute("recommendedList", listRecommendedProducts);
        model.addAttribute("id_product", id_product);
        if (id_product != null) {
            model.addAttribute("purchased_product", productService.getProductById(Integer.parseInt(id_product)));
        }

        model.addAttribute("hidden_category", true);
        model.addAttribute("value_category", 0);

        model.addAttribute("hidden_age", true);
        model.addAttribute("value_age", 0);

        model.addAttribute("hidden_gender", true);
        model.addAttribute("value_gender", 0);

        model.addAttribute("hidden_season", true);
        model.addAttribute("value_season", 0);


        if (authentication.getName().equals("anonymousUser")) {
            return "product/products";
        } else {
            User user = userService.getUserByUsername(authentication.getName());
            int cartId = user.getCart().getId_cart();
            user.setLast_log(convertDate(LocalDate.now()));
            userService.updateUser(user);
            model.addAttribute("quantity", cartService.getQuantityofCart(cartId));
            model.addAttribute("total", cartService.getTotalPrice(user.getCart().getId_cart()));

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

        model.addAttribute("id_product", null);


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
    public String filterget(@RequestParam(value = "listOfCategoryChecked", required = false) List<Integer> listOfCategoryCheckedint,
                            @RequestParam(value = "listOfAgesChecked", required = false) List<String> listOfAgesChecked,
                            @RequestParam(value = "listOfSeasonChecked", required = false) List<String> listOfSeasonChecked,
                            @RequestParam(value = "listOfGenderChecked", required = false) List<String> listOfGenderChecked,
                            @RequestParam(value = "price_min", required = false) String price_min,
                            @RequestParam(value = "price_max", required = false) String price_max,
                            @RequestParam(required = false) String drop_category,
                            @RequestParam(required = false) String drop_age,
                            @RequestParam(required = false) String drop_gender,
                            @RequestParam(required = false) String drop_season,
                            @RequestParam(value = "id_product", required = false) String id_product,
                            Model model) throws ParseException {




        List<Category> categoryCheckedList = new ArrayList<>();


        if (listOfCategoryCheckedint != null) {
            for (int i = 0; i < listOfCategoryCheckedint.size(); i++) {
                categoryCheckedList.add(categoryService.getCategoryById(listOfCategoryCheckedint.get(i)));
            }
        }

        List<Product> list = productService.getListOfProducts();

        //list = filtering(listOfCategoryCheckedint, listOfAgesChecked, listOfSeasonChecked, listOfGenderChecked, list, price_min, price_max, drop_category, drop_age, drop_gender, drop_season);
        list = filtering(listOfCategoryCheckedint, listOfAgesChecked, listOfSeasonChecked, listOfGenderChecked, list, "0", "99999", drop_category, drop_age, drop_gender, drop_season);

        model.addAttribute("categoryList", categoryService.getListOfCategories());
        model.addAttribute("agesList", productService.getListOfAges());
        model.addAttribute("genderList", productService.getListOfGenders());
        model.addAttribute("seasonList", productService.getListOfSeasons());

        model.addAttribute("categoryCheckedList", categoryCheckedList);
        model.addAttribute("categoryCheckedListint", listOfCategoryCheckedint);
        model.addAttribute("agesCheckedList", listOfAgesChecked);
        model.addAttribute("genderCheckedList", listOfGenderChecked);
        model.addAttribute("seasonCheckedList", listOfSeasonChecked);

        model.addAttribute("productList", list);
        model.addAttribute("price_min", price_min);
        model.addAttribute("price_max", price_max);

        showCategoryLi(drop_category, drop_age, drop_gender, drop_season, model);


        return "product/products";
    }

    void showCategoryLi(String drop_category, String drop_age, String drop_gender, String drop_season, Model model) {
        int next_value_category = pierwsza;
        int next_value_age = druga;
        int next_value_gender = trzecia;
        int next_value_season = czwarta;

        if (drop_category == null) {
            next_value_category = pierwsza;
        }
        if (drop_age == null) {
            next_value_age = druga;
        }
        if (drop_gender == null) {
            next_value_gender = trzecia;
        }
        if (drop_season == null) {
            next_value_season = czwarta;
        }


        if (drop_category != null) {
            next_value_category += 1;
        }
        if (drop_age != null) {
            next_value_age += 1;
        }
        if (drop_gender != null) {
            next_value_gender += 1;
        }
        if (drop_season != null) {
            next_value_season += 1;
        }

        if (next_value_category % 2 == 0) {
            model.addAttribute("hidden_category", true);
        } else if (next_value_category % 2 != 0) {
            model.addAttribute("hidden_category", false);
        }

        if (next_value_age % 2 == 0) {
            model.addAttribute("hidden_age", true);
        } else if (next_value_age % 2 != 0) {
            model.addAttribute("hidden_age", false);
        }

        if (next_value_gender % 2 == 0) {
            model.addAttribute("hidden_gender", true);
        } else if (next_value_gender % 2 != 0) {
            model.addAttribute("hidden_gender", false);
        }

        if (next_value_season % 2 == 0) {
            model.addAttribute("hidden_season", true);
        } else if (next_value_season % 2 != 0) {
            model.addAttribute("hidden_season", false);
        }

        model.addAttribute("value_category", next_value_category);
        model.addAttribute("value_age", next_value_age);
        model.addAttribute("value_genger", next_value_gender);
        model.addAttribute("value_season", next_value_season);

        pierwsza = next_value_category;
        druga = next_value_age;
        trzecia = next_value_gender;
        czwarta = next_value_season;

    }


    public List<Product> filtering(List<Integer> listOfCategoryCheckedint, List<String> listOfAgesChecked, List<String> listOfSeasonChecked, List<String> listOfGenderChecked, List<Product> list, String price_min, String price_max, String drop_category, String drop_age, String drop_gender, String drop_season) {
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

        //1000
        if (listOfCategoryCheckedint != null && listOfAgesChecked == null && listOfGenderChecked == null && listOfSeasonChecked == null) {
            list = list.stream()
                    .filter(p -> listOfCategoryCheckedint.contains(p.getId_category().getId_category()))
                    .collect(Collectors.toList());
        }
        //0100
        if (listOfCategoryCheckedint == null && listOfAgesChecked != null && listOfGenderChecked == null && listOfSeasonChecked == null) {
            list = list.stream()
                    .filter(p -> listOfAgesChecked.contains(p.getSize_age().getProduct_age()))
                    .collect(Collectors.toList());
        }
        //0010
        if (listOfCategoryCheckedint == null && listOfAgesChecked == null && listOfGenderChecked != null && listOfSeasonChecked == null) {

            if (listOfGenderChecked.size() > 1) {
                list = productRepository.findAllByGenderContaining("a");
            } else {
                for (int i = 0; i < list.size(); i++) {
                    for (int j = 0; j < listOfGenderChecked.size(); j++) {
                        list = productRepository.findAllByGenderContaining(listOfGenderChecked.get(j));
                    }
                }
            }
        }
        //0001
        if (listOfCategoryCheckedint == null && listOfAgesChecked == null && listOfGenderChecked == null && listOfSeasonChecked != null) {

            if (listOfSeasonChecked.contains("Całoroczne")) {
                list = productRepository.findAllBySeasonContaining("e");
            } else {
                list = list.stream()
                        .filter(p -> listOfSeasonChecked.contains(p.getSeason()))
                        .collect(Collectors.toList());
            }
        }


        //1100
        if (listOfCategoryCheckedint != null && listOfAgesChecked != null && listOfGenderChecked == null && listOfSeasonChecked == null) {
            list = list.stream()
                    .filter(p -> listOfCategoryCheckedint.contains(p.getId_category().getId_category()))
                    .filter(p -> listOfAgesChecked.contains(p.getSize_age().getProduct_age()))
                    .collect(Collectors.toList());
        }
        //1010
        if (listOfCategoryCheckedint != null && listOfAgesChecked == null && listOfGenderChecked != null && listOfSeasonChecked == null) {

            List<Product> help_List = list;

            if (listOfGenderChecked.size() > 1) {
                help_List = productRepository.findAllByGenderContaining("a");
            } else {
                for (int i = 0; i < list.size(); i++) {
                    for (int j = 0; j < listOfGenderChecked.size(); j++) {
                        help_List = productRepository.findAllByGenderContaining(listOfGenderChecked.get(j));
                    }
                }
            }

            help_List = list.stream()
                    .filter(p -> listOfCategoryCheckedint.contains(p.getId_category().getId_category()))
                    .collect(Collectors.toList());

            list = help_List;
        }
        //0110
        if (listOfCategoryCheckedint == null && listOfAgesChecked != null && listOfGenderChecked != null && listOfSeasonChecked == null) {

            List<Product> help_List = list;

            if (listOfGenderChecked.size() > 1) {
                help_List = productRepository.findAllByGenderContaining("a");
            } else {
                for (int i = 0; i < list.size(); i++) {
                    for (int j = 0; j < listOfGenderChecked.size(); j++) {
                        help_List = productRepository.findAllByGenderContaining(listOfGenderChecked.get(j));
                    }
                }
            }

            help_List = list.stream()
                    .filter(p -> listOfAgesChecked.contains(p.getSize_age().getProduct_age()))
                    .collect(Collectors.toList());

            list = help_List;
        }
        //0011
        if (listOfCategoryCheckedint == null && listOfAgesChecked == null && listOfGenderChecked != null && listOfSeasonChecked != null) {

            List<Product> help_List = list;

            if (listOfGenderChecked.size() > 1) {
                help_List = productRepository.findAllByGenderContaining("a");
            } else {
                for (int i = 0; i < list.size(); i++) {
                    for (int j = 0; j < listOfGenderChecked.size(); j++) {
                        help_List = productRepository.findAllByGenderContaining(listOfGenderChecked.get(j));
                    }
                }
            }

            help_List = list.stream()
                    .filter(p -> listOfSeasonChecked.contains(p.getSeason()))
                    .collect(Collectors.toList());

            list = help_List;

            if (listOfSeasonChecked.contains("Całoroczne")) {
                list = productRepository.findAllBySeasonContaining("e");
            } else {
                list = list.stream()
                        .filter(p -> listOfSeasonChecked.contains(p.getSeason()))
                        .collect(Collectors.toList());
            }
        }


        //1110
        if (listOfCategoryCheckedint != null && listOfAgesChecked != null && listOfGenderChecked != null && listOfSeasonChecked == null) {

            List<Product> help_List = list;

            if (listOfGenderChecked.size() > 1) {
                help_List = productRepository.findAllByGenderContaining("a");
            } else {
                for (int i = 0; i < list.size(); i++) {
                    for (int j = 0; j < listOfGenderChecked.size(); j++) {
                        help_List = productRepository.findAllByGenderContaining(listOfGenderChecked.get(j));
                    }
                }
            }

            help_List = list.stream()
                    .filter(p -> listOfCategoryCheckedint.contains(p.getId_category().getId_category()))
                    .filter(p -> listOfAgesChecked.contains(p.getSize_age().getProduct_age()))
                    .collect(Collectors.toList());

            list = help_List;
        }

        //1011
        if (listOfCategoryCheckedint != null && listOfAgesChecked == null && listOfGenderChecked != null && listOfSeasonChecked != null) {

            List<Product> help_List = list;

            if (listOfGenderChecked.size() > 1) {
                help_List = productRepository.findAllByGenderContaining("a");
            } else {
                for (int i = 0; i < list.size(); i++) {
                    for (int j = 0; j < listOfGenderChecked.size(); j++) {
                        help_List = productRepository.findAllByGenderContaining(listOfGenderChecked.get(j));
                    }
                }
            }

            help_List = list.stream()
                    .filter(p -> listOfCategoryCheckedint.contains(p.getId_category().getId_category()))
                    .collect(Collectors.toList());

            list = help_List;

            if (listOfSeasonChecked.contains("Całoroczne")) {
                list = productRepository.findAllBySeasonContaining("e");
            } else {
                list = list.stream()
                        .filter(p -> listOfSeasonChecked.contains(p.getSeason()))
                        .collect(Collectors.toList());
            }
        }
        //1101
        if (listOfCategoryCheckedint != null && listOfAgesChecked != null && listOfGenderChecked == null && listOfSeasonChecked != null) {

            List<Product> help_List = list;

            help_List = list.stream()
                    .filter(p -> listOfCategoryCheckedint.contains(p.getId_category().getId_category()))
                    .filter(p -> listOfAgesChecked.contains(p.getSize_age().getProduct_age()))
                    .collect(Collectors.toList());

            list = help_List;

            if (listOfSeasonChecked.contains("Całoroczne")) {
                list = productRepository.findAllBySeasonContaining("e");
            } else {
                list = list.stream()
                        .filter(p -> listOfSeasonChecked.contains(p.getSeason()))
                        .collect(Collectors.toList());
            }
        }
        //1111
        if (listOfCategoryCheckedint != null && listOfAgesChecked != null && listOfGenderChecked != null && listOfSeasonChecked != null) {

            List<Product> help_List = list;

            if (listOfGenderChecked.size() > 1) {
                help_List = productRepository.findAllByGenderContaining("a");
            } else {
                for (int i = 0; i < list.size(); i++) {
                    for (int j = 0; j < listOfGenderChecked.size(); j++) {
                        help_List = productRepository.findAllByGenderContaining(listOfGenderChecked.get(j));
                    }
                }
            }

            help_List = list.stream()
                    .filter(p -> listOfCategoryCheckedint.contains(p.getId_category().getId_category()))
                    .filter(p -> listOfAgesChecked.contains(p.getSize_age().getProduct_age()))
                    .collect(Collectors.toList());

            list = help_List;

            if (listOfSeasonChecked.contains("Całoroczne")) {
                list = productRepository.findAllBySeasonContaining("e");
            } else {
                list = list.stream()
                        .filter(p -> listOfSeasonChecked.contains(p.getSeason()))
                        .collect(Collectors.toList());
            }
        }
        //1001
        if (listOfCategoryCheckedint != null && listOfAgesChecked != null && listOfGenderChecked != null && listOfSeasonChecked != null) {

            List<Product> help_List = list;


            help_List = list.stream()
                    .filter(p -> listOfCategoryCheckedint.contains(p.getId_category().getId_category()))
                    .collect(Collectors.toList());

            list = help_List;

            if (listOfSeasonChecked.contains("Całoroczne")) {
                list = productRepository.findAllBySeasonContaining("e");
            } else {
                list = list.stream()
                        .filter(p -> listOfSeasonChecked.contains(p.getSeason()))
                        .collect(Collectors.toList());
            }
        }
        //0101
        if (listOfCategoryCheckedint == null && listOfAgesChecked != null && listOfGenderChecked == null && listOfSeasonChecked != null) {

            List<Product> help_List = list;


            help_List = list.stream()
                    .filter(p -> listOfAgesChecked.contains(p.getSize_age().getProduct_age()))
                    .collect(Collectors.toList());

            list = help_List;

            if (listOfSeasonChecked.contains("Całoroczne")) {
                list = productRepository.findAllBySeasonContaining("e");
            } else {
                list = list.stream()
                        .filter(p -> listOfSeasonChecked.contains(p.getSeason()))
                        .collect(Collectors.toList());
            }
        }
        return list;
    }

    public Date convertDate(LocalDate dateToConvert) {
        return java.sql.Date.valueOf(dateToConvert);
    }
}
