package com.shop.shop.service;

import com.shop.shop.Entity.Category;
import com.shop.shop.Repositories.CategoryRepository;
import com.shop.shop.Service.Impl.CategoryServiceImpl;
import com.shop.shop.Service.Interface.CategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;


@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceImplTests {

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    CategoryServiceImpl categoryService;

    @Test
    public void getAllCategories(){
        //given
        given(categoryRepository.findAll()).willReturn(prepareData());
        //when
        List<Category> categoryList = categoryService.getListOfCategories();
        //then
        assertThat(categoryList, hasSize(5));
    }

    private List<Category> prepareData() {
        Category category1 = new Category();
        Category category2 = new Category();
        Category category3 = new Category();
        Category category4 = new Category();
        Category category5 = new Category();
        return Arrays.asList(category1, category2, category3, category4, category5);
    }
}
