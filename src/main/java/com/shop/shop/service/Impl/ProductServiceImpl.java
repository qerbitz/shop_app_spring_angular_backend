package com.shop.shop.service.Impl;

import com.shop.shop.entity.Product;
import com.shop.shop.mapper.ReadDtoMapper;
import com.shop.shop.repositories.CategoryRepository;
import com.shop.shop.repositories.ProductRepository;
import com.shop.shop.service.Interface.ProductService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import com.shop.shop.Dto.ProductDto;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.shop.shop.mapper.ReadDtoMapper.*;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    //Wyszukiwanie produktu po id
    @Override
    public ProductDto getProductById(int id) throws NotFoundException {
        return productRepository.findById(id)
                .map(ReadDtoMapper::mapProductToDto)
                .orElseThrow(() -> new NotFoundException("No product found by id: "+id));
    }

   // @Cacheable(cacheNames = "allProductsList")
    public Page<Product> allProductsList(Pageable pageable, int sort_option) {
        return productRepository.findAll(handlePageResult(pageable,sort_option));
    }

    @Override
    public Page<Product> ByNameContainingProductsList(Pageable pageable,String keyword) {
        return productRepository.findByNameContaining(pageable, keyword);
    }

    @Override
    public Page<Product> ByCategoryProductsList(Pageable pageable, int id_category, int price_min, int price_max, int sort_option, String gender) {


        System.out.println(pageable);

        List<Product> allProductList;
        if(id_category==0){
            allProductList = productRepository.findAll();
        }
        else{
            allProductList = productRepository.findById_category(pageable, id_category).getContent();
        }

        List<Product> finalProductList = null;


        Predicate<Product> byPriceMin = p -> p.getPrice() > price_min;
        Predicate<Product> byPriceMax = person -> person.getPrice() < price_max;
        Predicate<Product> byGender = person -> person.getGender().equals(gender);

        List<Product> sortedProductList = allProductList.stream()
                .filter(byPriceMin).filter(byPriceMax).
                collect(Collectors.toList());

        System.out.println("raz"+sortedProductList.size());

        if(!gender.equals("all")) {
            List<Product> sortedProductList2 = sortedProductList.stream()
                    .filter(byGender).collect(Collectors.toList());



            System.out.println("dwa"+sortedProductList2.size());

            sortedProductList.clear();

            sortedProductList.addAll(sortedProductList2);

        }
        


            final int start = (int)pageable.getOffset();
            final int end = Math.min((start + pageable.getPageSize()), sortedProductList.size());
            final Page<Product> page = new PageImpl<>(sortedProductList.subList(start, end), pageable, sortedProductList.size());


            final Page<Product> page2 = new PageImpl<>(sortedProductList.subList(start, end), handlePageResult(page.getPageable(), sort_option), sortedProductList.size());
            return page2;
        //}
       //// else {
          //  return productRepository.findById_category(handlePageResult(pageable, sort_option), id_category);
       // }
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
