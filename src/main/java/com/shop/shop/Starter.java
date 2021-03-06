package com.shop.shop;

import com.shop.shop.entity.Category;
import com.shop.shop.entity.Product;
import com.shop.shop.repositories.CategoryRepository;
import com.shop.shop.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


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


        List<Category> listka12 = new ArrayList<>();
        listka12.add(category1);
        listka12.add(category2);

        List<Category> listka3 = new ArrayList<>();
        listka3.add(category1);
        listka3.add(category2);

        categoryRepository.save(category1);
        categoryRepository.save(category2);
        categoryRepository.save(category3);

        Product produkt1 = new Product();
        produkt1.setId_product(1);
        produkt1.setName("Bluza męska niebieska");
        produkt1.setPrice(59.99);
        produkt1.setDiscount(1.25);
        produkt1.setGender("Man");
        produkt1.setImage("../../../assets/blue_blouse.jpg");
        produkt1.setCategories(listka12);

        Product produkt2 = new Product();
        produkt2.setId_product(2);
        produkt2.setName("Bluza męska czerwona");
        produkt2.setPrice(129.99);
        produkt2.setGender("Man");
        produkt2.setImage("../../../assets/red_hoodie.jpg");
        produkt2.setCategories(listka3);

        Product produkt3 = new Product();
        produkt3.setId_product(3);
        produkt3.setName("Szara bluza");
        produkt3.setPrice(65.99);
        produkt3.setGender("Man");
        produkt3.setImage("../../../assets/grey_blous.jpg");
        produkt3.setCategories(listka12);

        Product produkt4 = new Product();
        produkt4.setId_product(4);
        produkt4.setName("Buty");
        produkt4.setPrice(199.99);
        produkt4.setDiscount(1.30);
        produkt4.setGender("Child");
        produkt4.setImage("../../../assets/white_shoe.jpg");
        //produkt4.setCategory(category2);

        Product produkt5 = new Product();
        produkt5.setId_product(5);
        produkt5.setName("Bluza męska");
        produkt5.setPrice(85.69);
        produkt5.setGender("Man");
        produkt5.setImage("../../../assets/dark_blouse.jpg");
        //produkt5.setCategory(category2);

        Product produkt6 = new Product();
        produkt6.setId_product(6);
        produkt6.setName("Buty dziecięce");
        produkt6.setPrice(159.99);
        produkt6.setGender("Child");
        produkt6.setImage("../../../assets/buty_szare_dzieciece.jpg");
       // produkt6.setCategory(category3);

        Product produkt7 = new Product();
        produkt7.setId_product(7);
        produkt7.setName("Buty treningowe damskie");
        produkt7.setPrice(189.99);
        produkt7.setGender("Woman");
        produkt7.setImage("../../../assets/buty_rozowe_woman.jpg");
        //produkt7.setCategory(category2);

        Product produkt8 = new Product();
        produkt8.setId_product(8);
        produkt8.setName("Obcasy niebieskie");
        produkt8.setPrice(229.99);
        produkt8.setGender("Woman");
        produkt8.setImage("../../../assets/obcas_blue_jpg.jpg");
        //produkt8.setCategory(category3);

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
