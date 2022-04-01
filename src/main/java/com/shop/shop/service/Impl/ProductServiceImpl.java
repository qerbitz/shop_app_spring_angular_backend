package com.shop.shop.service.Impl;

import com.shop.shop.entity.Category;
import com.shop.shop.entity.Product;
import com.shop.shop.repositories.CategoryRepository;
import com.shop.shop.repositories.ProductRepository;
import com.shop.shop.service.Interface.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    //Wyszukiwanie produktu po id
    @Override
    public Product getProductById(int id) {
        return productRepository.getOne(id);
    }

    @Cacheable(cacheNames = "allProductsList")
    public Page<Product> allProductsList(Pageable pageable, int sort_option) {
        return productRepository.findAll(handlePageResult(pageable,sort_option));
    }

    @Override
    public Page<Product> ByNameContainingProductsList(Pageable pageable,String keyword) {
        return productRepository.findByNameContaining(pageable, keyword);
    }

    @Override
    public Page<Product> ByCategoryProductsList(Pageable pageable, int id_category, int price, int sort_option, String gender) {


        List<Product> tako = new ArrayList<>();
        if(id_category==0){
            tako = productRepository.findAll(pageable).getContent();
        }
        else{
            tako = productRepository.findById_category(pageable, id_category).getContent();
        }
        List<Product> tako2 = new ArrayList<>();
        if(!gender.equals("all")){
            for(int i=0; i<tako.size(); i++){
                if(tako.get(i).getGender().equals(gender)){
                    tako2.add(tako.get(i));
                }
            }

            final int start = (int)pageable.getOffset();
            final int end = Math.min((start + pageable.getPageSize()), tako2.size());
            final Page<Product> page = new PageImpl<>(tako2.subList(start, end), pageable, tako2.size());

            return page;
        }
        else {
            return productRepository.findById_category(handlePageResult(pageable, sort_option), id_category);
        }
    }

    @Override
    public List<Product> BySaleProductsList() {
        List<Product> productList = new ArrayList<>();

        for (Object[] obj : productRepository.findAllBySaleDesc()) {
            String product_id = String.valueOf(obj[0]);
            productList.add(productRepository.getOne(Integer.parseInt(product_id)));
        }
        return productList;
    }

    public Pageable handlePageResult(Pageable pageable, int sort_option){
        //It's just default initialization
        Pageable page = PageRequest.of(0,15);
        int page_number = pageable.getPageNumber();
        int page_size = pageable.getPageSize();

        switch (sort_option){
            case 0:{
                page = PageRequest.of(page_number, page_size);
                break;
            }
            case 1:{
                page = PageRequest.of(page_number, page_size, Sort.by("name").ascending());
                break;
            }
            case 2:{
                page = PageRequest.of(page_number, page_size, Sort.by("name").descending());
                break;
            }
            case 3:{
                page = PageRequest.of(page_number, page_size, Sort.by("price").ascending());
                break;
            }
            case 4:{
                page = PageRequest.of(page_number, page_size, Sort.by("price").descending());
                break;
            }
        }
        return page;
    }


}
