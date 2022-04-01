package com.shop.shop.service.Impl;

import com.shop.shop.entity.Category;
import com.shop.shop.repositories.CategoryRepository;
import com.shop.shop.service.Interface.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    @Cacheable("ListOfCategories")
    public List<Category> getListOfCategories() {
        return categoryRepository.findAll();
    }

}
