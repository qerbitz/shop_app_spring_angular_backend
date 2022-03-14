package com.shop.shop.service.Interface;

import com.shop.shop.entity.Product;
import org.springframework.data.domain.Page;

import java.util.List;


public interface ProductService {

    List<Product> getListofAvaliableProductsByName(String name);

    List<String> getListOfSizesBy(int category_id);

    String getAgeOfSize(String size);

    List<String> getListOfGenders();

    List<Product> getListOfProducts();

    List<Product> getListOfProductsByCategory(int category);

    List<Product> getListOfProductsOrderByNameAsc();

    List<Product> getListOfProductsOrderByNameDesc();

    List<Product> getListOfProductOrderByPriceAsc();

    List<Product> getListOfProductOrderByPriceDesc();

    List<Product> getListOfProductByPriceBetween(int price_min, int price_max);

    List<Product> getListOfProductsByAgeContaining(int nowy_poczatek, int nowy_koniec, List<Product> proponowane, String season, String gender);

    List<Product> getListOfProductsWithDiscount();

    List<String> getListOfAges();

    List<String> getListOfSeasons();

    Page<Product> listAll(int pageNum, int pageSize);

    Product getProductById(int id);



}
