package com.shop.shop.Service.Impl;

import com.shop.shop.Entity.Product;
import com.shop.shop.Repositories.ProductRepository;
import com.shop.shop.Service.Interface.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public List<Product> getListOfProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getListOfProductsByName(String name) {
        return productRepository.findByNameContaining(name);
    }

    @Override
    public List<Product> getListOfProductsByCategory(int category) {
        return productRepository.findById_category(category);
    }

    @Override
    public List<Product> getListOfProductsOrderByNameAsc() {
        return productRepository.findAllByOrderByNameAsc();
    }

    @Override
    public List<Product> getListOfProductsOrderByNameDesc() {
        return productRepository.findAllByOrderByNameDesc();
    }

    @Override
    public List<Product> getListOfProductOrderByPriceAsc() {
        return productRepository.findAllByOrderByPriceAsc();
    }

    @Override
    public List<Product> getListOfProductOrderByPriceDesc() {
        return productRepository.findAllByOrderByPriceDesc();
    }

    @Override
    public List<Product> getListOfProductByPriceBetween(int price_min, int price_max) {
        return productRepository.findAllByPriceBetween(price_min, price_max);
    }

    @Override
    public Product getSingleProduct(int id) {
        return productRepository.getOne(id);
    }


}
