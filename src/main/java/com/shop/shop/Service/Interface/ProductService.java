package com.shop.shop.Service.Interface;

import com.shop.shop.Entity.Product;
import java.util.List;
import java.util.Map;


public interface ProductService {


    List<Product> getListOfProducts();

    List<Product> getListOfProductsByName(String name);

    List<Product> getListOfProductsByCategory(int category);

    List<Product> getListOfProductsOrderByNameAsc();

    List<Product> getListOfProductsOrderByNameDesc();

    List<Product> getListOfProductOrderByPriceAsc();

    List<Product> getListOfProductOrderByPriceDesc();

    List<Product> getListOfProductsOrderBySaleDesc();

    List<Product> getListOfProductByPriceBetween(int price_min, int price_max);

    List<Product> getListOfProductsByAgeContaining(String age);

    List<Product> findByCriteria(Map<String, List<String>> filterParams, List<Product> products);

    List<String> getListOfAges();

    Product getProductById(int id);

    boolean changeQuantityOfProduct(Product product, int quantity);


}
