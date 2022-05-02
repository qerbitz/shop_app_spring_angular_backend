package com.shop.shop.mapper;

import com.shop.shop.Dto.CategoryDto;
import com.shop.shop.Dto.ProductDto;
import com.shop.shop.entity.Category;
import com.shop.shop.entity.Product;

import java.util.List;
import java.util.stream.Collectors;

public class ReadDtoMapper {


    public static List<ProductDto> mapProductToProductReadDtoList(List<Product> products) {
        return products.stream()
                .map(product -> mapProductToDto(product))
                .collect(Collectors.toList());
    }

    private static ProductDto mapProductToDto(Product product) {
        return ProductDto.ProductDtoBuilder.aProductDto()
                .withName(product.getName())
                .withPrice(product.getPrice())
                .withImage(product.getImage())
                .build();
    }


    public static List<CategoryDto> mapCategoryToPostReadDtoList(List<Category> categories) {
        return categories.stream()
                .map(category -> mapCategoryToDto(category))
                .collect(Collectors.toList());
    }

    private static CategoryDto mapCategoryToDto(Category category) {
        return CategoryDto.CategoryDtoBuilder.aCategoryDto()
                .withId(category.getId_category())
                .withName(category.getName())
                .build();
    }
}
