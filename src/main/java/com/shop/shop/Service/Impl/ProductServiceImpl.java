package com.shop.shop.Service.Impl;

import com.shop.shop.Entity.Producent;
import com.shop.shop.Entity.Product;
import com.shop.shop.Repositories.ProducentRepository;
import com.shop.shop.Repositories.ProductRepository;
import com.shop.shop.Service.Interface.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProducentRepository producentRepository;

    @Override
    public List<Product> getListofAvaliableProductsByName(String name) {

        List<Product> AvaliableOtherSizes = new ArrayList<>();


        for(Object[] obj: productRepository.findAvaliableProductsByName(name)){
            int ajdi = Integer.parseInt(String.valueOf(obj[0]));
            //String size = String.valueOf(obj[1]);

            AvaliableOtherSizes.add(productRepository.getOne(ajdi));
        }

        for(int i=0;i<AvaliableOtherSizes.size();i++){
            for(int j=AvaliableOtherSizes.size()-1; j>0; j--){
                if(AvaliableOtherSizes.get(i).getSize_age().getProduct_size()==AvaliableOtherSizes.get(j).getSize_age().getProduct_size()){
                    AvaliableOtherSizes.remove(i);
                }
            }
        }

        return AvaliableOtherSizes;
    }

    @Override
    public List<String> getListOfSizesBy(int category_id) {

        List<String> sizesList = new ArrayList<>();

        for (Object[] obj : productRepository.findAllSizesByCategoryId(category_id)) {
            String size = String.valueOf(obj[0]);
            sizesList.add(size);
        }

        return sizesList;
    }

    @Override
    public String getAgeOfSize(String size) {
        return productRepository.getAgeOfSize(size);
    }

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
    public List<Producent> getListOfProducents() {
        return producentRepository.findAll();
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
    public List<Product> getListOfProductsByAgeContaining(int nowy_poczatek, int nowy_koniec, List<Product> proponowane, String season, String gender) {
        List<Product> proponowaneNowe = new ArrayList<>();

        Set<Product> productSet1 = new HashSet<>();

        //Pomiedzy 2 a 4 mscem
        if (isBetween(2, 4, nowy_poczatek) || isBetween(2, 4, nowy_koniec)) {
            proponowaneNowe = findLoop(productRepository.findAllByAgeAndSeasonContaining("2-4 msc", season, gender), proponowane);
        }
        //Pomiedzy 2 a 6 mscem
        if (isBetween(2, 6, nowy_poczatek) || isBetween(2, 6, nowy_koniec)) {
            proponowaneNowe = findLoop(productRepository.findAllByAgeAndSeasonContaining("0-6 msc", season, gender), proponowane);
        }
        //Pomiedzy 4 a 6 mscem
        if (isBetween(4, 6, nowy_poczatek) || isBetween(4, 6, nowy_koniec)) {
            proponowaneNowe = findLoop(productRepository.findAllByAgeAndSeasonContaining("4-6 msc", season, gender), proponowane);
        }
        //Pomiedzy 6 a 9 mscem
        if (isBetween(6, 9, nowy_poczatek) || isBetween(6, 9, nowy_koniec)) {
            proponowaneNowe = findLoop(productRepository.findAllByAgeAndSeasonContaining("6-9 msc", season, gender), proponowane);
        }
        //Pomiedzy 6 a 12 mscem
        if (isBetween(6, 12, nowy_poczatek) || isBetween(6, 12, nowy_koniec)) {
            proponowaneNowe = findLoop(productRepository.findAllByAgeAndSeasonContaining("6-12 msc", season, gender), proponowane);
        }
        //Pomiedzy 9 a 12 mscem
        if (isBetween(9, 12, nowy_poczatek) || isBetween(9, 12, nowy_koniec)) {
            proponowaneNowe = findLoop(productRepository.findAllByAgeAndSeasonContaining("9-12 msc", season, gender), proponowane);
        }
        //Pomiedzy 1 a 1,5 rokiem
        if (isBetween(12, 18, nowy_poczatek) || isBetween(12, 18, nowy_koniec)) {
            proponowaneNowe = findLoop(productRepository.findAllByAgeAndSeasonContaining("12-18 msc", season, gender), proponowane);
        }
        //Pomiedzy 1 a 2 rokiem
        if (isBetween(12, 24, nowy_poczatek) || isBetween(12, 24, nowy_koniec)) {
            proponowaneNowe = findLoop(productRepository.findAllByAgeAndSeasonContaining("12-24 msc", season, gender), proponowane);
        }
        //Pomiedzy 1,5 a 2 rokiem
        if (isBetween(18, 24, nowy_poczatek) || isBetween(18, 24, nowy_koniec)) {
            proponowaneNowe = findLoop(productRepository.findAllByAgeAndSeasonContaining("18-24 msc", season, gender), proponowane);
        }
        //Pomiedzy 2 a 3 rokiem
        if (isBetween(24, 36, nowy_poczatek) || isBetween(24, 36, nowy_koniec)) {
            proponowaneNowe = findLoop(productRepository.findAllByAgeAndSeasonContaining("24-36 msc", season, gender), proponowane);
        }
        //Pomiedzy 2 a 4 rokiem
        if (isBetween(24, 48, nowy_poczatek) || isBetween(24, 48, nowy_koniec)) {
            proponowaneNowe = findLoop(productRepository.findAllByAgeAndSeasonContaining("24-48 msc", season, gender), proponowane);
        }
        //Pomiedzy 3 a 4 rokiem
        if (isBetween(36, 48, nowy_poczatek) || isBetween(36, 48, nowy_koniec)) {
            proponowaneNowe = findLoop(productRepository.findAllByAgeAndSeasonContaining("36-48 msc", season, gender), proponowane);
        }
        //Pomiedzy 4 a 5 rokiem
        if (isBetween(48, 60, nowy_poczatek) || isBetween(48, 60, nowy_koniec)) {
            proponowaneNowe = findLoop(productRepository.findAllByAgeAndSeasonContaining("48-60 msc", season, gender), proponowane);
        }
        //Pomiedzy 5 a 6 rokiem
        if (isBetween(60, 72, nowy_poczatek) || isBetween(60, 72, nowy_koniec)) {
            proponowaneNowe = findLoop(productRepository.findAllByAgeAndSeasonContaining("60-72", season, gender), proponowane);
        }

        if (!proponowaneNowe.isEmpty()) {
            proponowaneNowe.removeIf(yourInt -> !productSet1.add(yourInt));
        }

        return proponowaneNowe;
    }

    @Override
    public List<String> getListOfAges() {
        List<String> listOfAges2 = new ArrayList<>();


        listOfAges2.add("0-2 msc");
        listOfAges2.add("2-4 msc");
        listOfAges2.add("2-6 msc");
        listOfAges2.add("4-6 msc");
        listOfAges2.add("6-9 msc");
        listOfAges2.add("6-12 msc");
        listOfAges2.add("9-12 msc");
        listOfAges2.add("12-18 msc");
        listOfAges2.add("12-24 msc");
        listOfAges2.add("18-24 msc");
        listOfAges2.add("2-3 lata");
        listOfAges2.add("3-4 lata");
        listOfAges2.add("4-5 lat");
        listOfAges2.add("5-6 lat");


        return listOfAges2;
    }


    @Override
    public List<String> getListOfSeasons() {
        List<String> listofSeasons = new ArrayList<>();

        for(Object[] obj : productRepository.findAllSeasons()){
            String season = String.valueOf(obj[0]);
            listofSeasons.add(season);
        }
        return listofSeasons;
    }


    //Wyszukiwanie produktu po id
    @Override
    public Product getProductById(int id) {
        return productRepository.getOne(id);
    }

    @Override
    public Producent getProducentById(int id) {
        return producentRepository.getOne(id);
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

    public Page<Product> listAll(int pageNum, int pageSize) {

        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);

        return productRepository.findAll(pageable);
    }


}
