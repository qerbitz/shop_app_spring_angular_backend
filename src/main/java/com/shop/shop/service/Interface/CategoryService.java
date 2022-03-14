package com.shop.shop.service.Interface;

import com.shop.shop.entity.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getListOfCategories();

    Category getCategoryById(int id);
}
