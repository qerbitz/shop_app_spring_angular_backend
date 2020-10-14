package com.shop.shop.Service.Impl;

import com.shop.shop.Entity.Category;
import com.shop.shop.Entity.Order;
import com.shop.shop.Entity.Product;
import com.shop.shop.Repositories.ProductRepository;
import com.shop.shop.Service.Interface.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
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
    public List<Product> getListOfProductsByAgeContaining(int nowy_poczatek, int nowy_koniec, Order order, List<Product> proponowane) {
        List<Product> proponowaneNowe = new ArrayList<>();
        List<Product> tescik = new ArrayList<>();


        Set<Product> productSet1 = new HashSet<>();

        //Wyszukiwanie wszystkich produktów z podanego przedzialu wiekowego
        for(int i=nowy_poczatek; i<=nowy_koniec; i++)
        {
            String pomoc = String.valueOf(i);
            proponowaneNowe = findLoop(productRepository.findAllByAgeContaining(pomoc), proponowane);
        }

        for(int i=0; i<proponowaneNowe.size(); i++){
            int freq = Collections.frequency(proponowaneNowe, proponowaneNowe.get(i));
            if(freq>1)
            tescik.add(proponowaneNowe.get(i));

            System.out.println(proponowaneNowe.get(i).getName());
        }


        if(!tescik.isEmpty()){
            //Usuwanie powtarzalnosci
            tescik.removeIf(yourInt -> !productSet1.add(yourInt));
            //Filtrowanie ze względu na kategorie
            List<Product> filteredlist = tescik.stream()
                    .filter(p -> p.getId_category().getName() == order.getCart().getCartItems().get(0).getProduct().getId_category().getName())
                    .collect(Collectors.toList());

            return filteredlist;
        }
        else{
            //Usuwanie powtarzalnosci
            if(!proponowaneNowe.isEmpty()){
                proponowaneNowe.removeIf(yourInt -> !productSet1.add(yourInt));
            }
            //Filtrowanie ze względu na kategorie
            List<Product> filteredlist = proponowaneNowe.stream()
                    .filter(p -> p.getId_category().getName() == order.getCart().getCartItems().get(0).getProduct().getId_category().getName())
                    .collect(Collectors.toList());
            
            return filteredlist;
        }
    }
    public List<Product> findLoop(List<Product> cosiktam, List<Product> proponowane){
        proponowane.addAll(cosiktam);

        return proponowane;
    }


    @Override
    public List<String> getListOfAges() {
        List<String> listOfAges = new ArrayList<>();

        for(Object[] obj: productRepository.findAllAges()){
            String age = String.valueOf(obj[0]);
            if(age.contains("24-36")){
                age = "2-3 lata";
            }
            if(age.contains("36-48")){
                age = "3-4 lata";
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
