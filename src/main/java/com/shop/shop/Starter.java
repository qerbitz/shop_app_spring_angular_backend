package com.shop.shop;

import com.shop.shop.entity.Category;
import com.shop.shop.entity.Product;
import com.shop.shop.repositories.CategoryRepository;
import com.shop.shop.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Component
public class Starter {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public Starter(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {

        Category category1 = new Category();
        category1.setId_category(1);
        category1.setName("pierwsza");


        Category category2 = new Category();
        category2.setId_category(2);
        category2.setName("druga");


        Category category3 = new Category();
        category3.setId_category(3);
        category3.setName("trzecia");

        categoryRepository.save(category1);
        categoryRepository.save(category2);
        categoryRepository.save(category3);

        Product produkt1 = new Product();
        produkt1.setId_product(1);
        produkt1.setName("buty");
        produkt1.setPrice(13.69);
        produkt1.setDiscount(1.25);
        produkt1.setGender("Man");
        produkt1.setCategory(category1);

        Product produkt2 = new Product();
        produkt2.setId_product(2);
        produkt2.setName("czapka");
        produkt2.setPrice(132.69);
        produkt2.setGender("Man");
        produkt2.setCategory(category1);

        Product produkt3 = new Product();
        produkt3.setId_product(3);
        produkt3.setName("arafatka");
        produkt3.setPrice(135.69);
        produkt3.setGender("Woman");
        produkt3.setCategory(category1);

        Product produkt4 = new Product();
        produkt4.setId_product(4);
        produkt4.setName("żeżucha");
        produkt4.setPrice(12.61);
        produkt4.setDiscount(1.30);
        produkt4.setGender("Man");
        produkt4.setCategory(category2);

        Product produkt5 = new Product();
        produkt5.setId_product(5);
        produkt5.setName("grzebyk");
        produkt5.setPrice(15.69);
        produkt5.setGender("Woman");
        produkt5.setCategory(category2);

        Product produkt6 = new Product();
        produkt6.setId_product(6);
        produkt6.setName("szklanka");
        produkt6.setPrice(15.69);
        produkt6.setGender("Child");
        produkt6.setCategory(category3);

        Product produkt7 = new Product();
        produkt7.setId_product(7);
        produkt7.setName("tłok");
        produkt7.setPrice(152.69);
        produkt7.setGender("Woman");
        produkt7.setCategory(category2);

        Product produkt8 = new Product();
        produkt8.setId_product(8);
        produkt8.setName("zatrzask");
        produkt8.setPrice(29.99);
        produkt7.setGender("Man");
        produkt8.setCategory(category3);

        productRepository.save(produkt1);
        productRepository.save(produkt2);
        productRepository.save(produkt3);
        productRepository.save(produkt4);
        productRepository.save(produkt5);
        productRepository.save(produkt6);
        productRepository.save(produkt7);
        productRepository.save(produkt8);

    }
}
