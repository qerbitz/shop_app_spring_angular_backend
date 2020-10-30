package com.shop.shop.Service.Interface;

import com.shop.shop.Entity.Product;
import java.util.List;


public interface ProductService {

    List<String> getListOfGenders();

    List<Product> getListOfProducts();

    List<Product> getListOfProductsByName(String name);

    List<Product> getListOfProductsByCategory(int category);

    List<Product> getListOfProductsOrderByNameAsc();

    List<Product> getListOfProductsOrderByNameDesc();

    List<Product> getListOfProductOrderByPriceAsc();

    List<Product> getListOfProductOrderByPriceDesc();

    List<Product> getListOfProductsOrderBySaleDesc();

    List<Product> getListOfProductByPriceBetween(int price_min, int price_max);

    List<Product> getListOfProductsByAgeContaining(int nowy_poczatek, int nowy_koniec, List<Product> proponowane, String season);

    List<String> getListOfAges();

    Product getProductById(int id);

    boolean changeQuantityOfProduct(Product product, int quantity);


}
