package com.shop.shop.Service.Impl;

import com.shop.shop.Entity.Category;
import com.shop.shop.Entity.Product;
import com.shop.shop.Repositories.ProductRepository;
import com.shop.shop.Service.Interface.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public List<String> getListOfGenders() {
        List<String> genderList = new ArrayList<>();
        genderList.add("Chłopak");
        genderList.add("Dziewczynka");
        return genderList;
    }

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

        for (Object[] obj : productRepository.findAllBySaleDesc()) {
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
    public List<Product> getListOfProductsByAgeContaining(int nowy_poczatek, int nowy_koniec, List<Product> proponowane) {
        List<Product> proponowaneNowe = new ArrayList<>();

        Set<Product> productSet1 = new HashSet<>();

        //Pomiedzy 2 a 4 mscem
        if (isBetween(2, 4, nowy_poczatek) || isBetween(2, 4, nowy_koniec)) {
            proponowaneNowe = findLoop(productRepository.findAllByAge("2-4 msc"), proponowane);
        }
        //Pomiedzy 4 a 6 mscem
        if (isBetween(4, 6, nowy_poczatek) || isBetween(4, 6, nowy_koniec)) {
            proponowaneNowe = findLoop(productRepository.findAllByAge("4-6 msc"), proponowane);
        }
        //Pomiedzy 6 a 9 mscem
        if (isBetween(6, 9, nowy_poczatek) || isBetween(6, 9, nowy_koniec)) {
            proponowaneNowe = findLoop(productRepository.findAllByAge("6-9 msc"), proponowane);
        }
        //Pomiedzy 9 a 12 mscem
        if (isBetween(9, 12, nowy_poczatek) || isBetween(9, 12, nowy_koniec)) {
            proponowaneNowe = findLoop(productRepository.findAllByAge("9-12 msc"), proponowane);
        }
        //Pomiedzy 1 a 1,5 rokiem
        if (isBetween(12, 18, nowy_poczatek) && isBetween(12, 18, nowy_koniec)) {
            proponowaneNowe = findLoop(productRepository.findAllByAge("12-18 msc"), proponowane);
        }
        //Pomiedzy 1,5 a 2 rokiem
        if (isBetween(18, 24, nowy_poczatek) && isBetween(18, 24, nowy_koniec)) {
            proponowaneNowe = findLoop(productRepository.findAllByAge("18-24 msc"), proponowane);
        }
        //Pomiedzy 2 a 3 rokiem
        if (isBetween(24, 36, nowy_poczatek) && isBetween(24, 36, nowy_koniec)) {
            proponowaneNowe = findLoop(productRepository.findAllByAge("24-36"), proponowane);
        }
        //Pomiedzy 3 a 4 rokiem
        if (isBetween(36, 48, nowy_poczatek) && isBetween(36, 48, nowy_koniec)) {
            proponowaneNowe = findLoop(productRepository.findAllByAge("36-48"), proponowane);
        }
        //Pomiedzy 4 a 5 rokiem
        if (isBetween(48, 60, nowy_poczatek) && isBetween(48, 60, nowy_koniec)) {
            proponowaneNowe = findLoop(productRepository.findAllByAge("48-60"), proponowane);
        }
        //Pomiedzy 5 a 6 rokiem
        if (isBetween(60, 72, nowy_poczatek) && isBetween(60, 72, nowy_koniec)) {
            proponowaneNowe = findLoop(productRepository.findAllByAge("60-72"), proponowane);
        }

         if(!proponowaneNowe.isEmpty()) {
            proponowaneNowe.removeIf(yourInt -> !productSet1.add(yourInt));
        }

        return proponowaneNowe;
    }

    @Override
    public List<String> getListOfAges() {
        List<String> listOfAges = new ArrayList<>();

        for (Object[] obj : productRepository.findAllAges()) {
            String age = String.valueOf(obj[0]);
            if (age.contains("24-36")) {
                age = "2-3 lata";
            }
            if (age.contains("36-48")) {
                age = "3-4 lata";
            }
            if (age.contains("48-60")) {
                age = "4-5 lata";
            }
            if (age.contains("60-72")) {
                age = "5-6 lata";
            }
            if (age.contains("72-84")) {
                age = "7-8 lata";
            }
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
        if (product.getQuantity() > 0) {
            product.setQuantity(product.getQuantity() - quantity);
            productRepository.save(product);
            return true;
              } else {
            return false;
        }
    }

    public static boolean isBetween(int a, int b, int c) {
        return b > a ? c >= a && c <= b : c >= b && c < a;
    }

    public List<Product> findLoop(List<Product> cosiktam, List<Product> proponowane) {
        proponowane.addAll(cosiktam);

        return proponowane;
    }


}
