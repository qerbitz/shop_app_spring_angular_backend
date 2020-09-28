package com.shop.shop.Service.Impl;

import com.shop.shop.Entity.Product;
import com.shop.shop.Repositories.ProductRepository;
import com.shop.shop.Service.Interface.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public List<Product> getListOfProductsByAgeContaining(String age) {
        return productRepository.findAllByAgeContaining(age);
    }

    @Override
    public List<Product> findByCriteria(Map<String, List<String>> filterParams,  List<Product> products) {

        List<String> categoryCriteria = filterParams.get("category").stream().map(f -> f.toLowerCase()).collect(Collectors.toList());
        List<String> ageCriteria = filterParams.get("age").stream().map(f -> f.toLowerCase()).collect(Collectors.toList());
        //List<String> priceCriteria = filterParams.get("price").stream().map(f -> f.toLowerCase()).collect(Collectors.toList());
        //List<String> sizeCriteria = filterParams.get("size").stream().map(f -> f.toLowerCase()).collect(Collectors.toList());

        List<String> finalCategoryCriteria = categoryCriteria;
        List<Product> list = products.stream()
                 .filter(p -> ageCriteria.contains(p.getAge().toLowerCase()))
                 .filter(p -> categoryCriteria.contains(p.getId_category().getName().toLowerCase()))
                 //.filter(p -> priceCriteria.c)
                .collect(Collectors.toList());

        if (list.isEmpty()) {
            //throw new IllegalArgumentException("Did not find elements");
            System.out.println("chuja");
        }

        return list;
    }

    @Override
    public List<String> getListOfAges() {
        List<String> listOfAges = new ArrayList<>();

        for(Object[] obj: productRepository.findAllAges()){
            String age = String.valueOf(obj[0]);
            listOfAges.add(age);
        }

        return listOfAges;
    }


    //Wyszukiwanie produktu po id
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
