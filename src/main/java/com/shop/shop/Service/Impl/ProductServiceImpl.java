package com.shop.shop.Service.Impl;

import com.shop.shop.Entity.Product;
import com.shop.shop.Repositories.ProductRepository;
import com.shop.shop.Service.Interface.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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


    //Wyswietlanie produktow najpopularniejszych wobed sprzedazy malejaco
    @Override
    public List<Product> getListOfProductsOrderBySaleDesc() {
        List<Product> productList = new ArrayList<>();

        for(Object[] obj: productRepository.findAllBySaleDesc()){
            String product = String.valueOf(obj[0]);
            productList.add(productRepository.getOne(Integer.parseInt(product)));
        }
        return productList;
    }

    @Override
    public List<Product> getListOfProductByPriceBetween(int price_min, int price_max) {
        return productRepository.findAllByPriceBetween(price_min, price_max);
    }

    @Override
    public Product getProductById(int id) {
        return productRepository.getOne(id);
    }


    //Funkcja do zmiany ilosci produktow w asortymencie
    @Override
    public boolean changeQuantityOfProduct(Product product, int quantity) {
        if(product.getQuantity()>0){
            product.setQuantity(product.getQuantity()-quantity);
            productRepository.save(product);
            return true;
        }
        else{
            return false;
        }
    }

}
