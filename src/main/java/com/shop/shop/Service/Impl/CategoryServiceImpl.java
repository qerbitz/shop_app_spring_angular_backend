package com.shop.shop.Service.Impl;

import com.shop.shop.Entity.Category;
import com.shop.shop.Repositories.CategoryRepository;
import com.shop.shop.Service.Interface.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Category> getListOfCategories() {
        return categoryRepository.findAll();
    }
}
