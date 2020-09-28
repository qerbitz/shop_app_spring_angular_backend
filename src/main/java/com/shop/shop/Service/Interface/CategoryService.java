package com.shop.shop.Service.Interface;

import com.shop.shop.Entity.Category;
import com.shop.shop.Entity.Product;

import java.util.List;

public interface CategoryService {

    List<Category> getListOfCategories();

    Category getCategoryById(int id);
}
