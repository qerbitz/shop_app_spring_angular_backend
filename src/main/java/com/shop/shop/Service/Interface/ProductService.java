package com.shop.shop.Service.Interface;

import com.shop.shop.Entity.Product;

import java.util.List;
import java.util.Optional;


public interface ProductService {


    List<Product> getListOfProducts();

    List<Product> getListOfProductsByName(String name);

    List<Product> getListOfProductsByCategory(int category);

    void updateQuantity(int productId, Integer newQuantity);

    Optional<Product> getSingleProduct(int id_Product);

}
