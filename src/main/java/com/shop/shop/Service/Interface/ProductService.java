package com.shop.shop.Service.Interface;

import com.shop.shop.Entity.Product;
import java.util.List;


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

    Product getProductById(int id);

}
