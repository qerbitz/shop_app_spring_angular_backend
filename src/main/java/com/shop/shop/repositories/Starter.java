package com.shop.shop.repositories;

import com.shop.shop.entity.Category;
import com.shop.shop.entity.Product;
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
        category1.setName("testowa_kategoria");
        categoryRepository.save(category1);

        Product produkt1 = new Product();
        produkt1.setId_product(1);
        produkt1.setName("testowanko");
        produkt1.setCategory(category1);

        productRepository.save(produkt1);

    }
}
