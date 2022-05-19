package com.shop.shop.service.Interface;

import com.shop.shop.Dto.ProductDto;
import com.shop.shop.entity.Product;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {


    Page<Product> allProductsList(Pageable pageable, int sort_option);

    Page<Product> ByNameContainingProductsList(Pageable pageable, String keyword);

    Page<Product> ByCategoryProductsList(Pageable pageable, int id_category, int price_min, int price_max, int sort_option, String gender);

    List<Product> BySaleProductsList();

    ProductDto getProductById(int id) throws NotFoundException;

}
