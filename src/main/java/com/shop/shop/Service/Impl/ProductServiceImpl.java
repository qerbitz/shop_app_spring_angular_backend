package com.shop.shop.Service.Impl;

import com.shop.shop.Entity.Product;
import com.shop.shop.Repositories.ProductRepository;
import com.shop.shop.Service.Interface.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public List<Product> getListOfProducts() {
        return productRepository.findAll();
    }

    @Override
    public void updateQuantity(int productId, Integer newQuantity) {
        Optional<Product> tempProduct = getSingleProduct(productId);
        tempProduct.ifPresent(product -> {
           // product.setQuantity(newQuantity);
            productRepository.save(product);
        });

    }

    @Override
    public Optional<Product> getSingleProduct(int idOfProduct) {
        return productRepository.findById(idOfProduct);
    }
}
