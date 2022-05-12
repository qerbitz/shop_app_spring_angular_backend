package com.shop.shop.mapper;

import com.shop.shop.Dto.CategoryDto;
import com.shop.shop.Dto.OrderProductsDto;
import com.shop.shop.Dto.OrderDto;
import com.shop.shop.Dto.ProductDto;
import com.shop.shop.entity.Category;
import com.shop.shop.entity.Order;
import com.shop.shop.entity.OrderItem;
import com.shop.shop.entity.Product;

import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;


public class ReadDtoMapper {


    public static List<ProductDto> mapProductToProductReadDtoList(List<Product> products) {
        return products.stream()
                .map(product -> mapProductToDto(product))
                .collect(Collectors.toList());
    }

    public static ProductDto mapProductToDto(Product product) {
        return ProductDto.ProductDtoBuilder.aProductDto()
                .withId(product.getId_product())
                .withName(product.getName())
                .withPrice(product.getPrice())
                .withImage(product.getImage())
                .build();
    }

//
    public static List<CategoryDto> mapCategoryToCategoryReadDtoList(List<Category> categories) {
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

//
    public static List<OrderDto> mapOrderToOrderListReadDtoList(List<Order> orders) {
        return orders.stream()
                .map(order -> mapOrderToDto(order))
                .collect(Collectors.toList());
    }

    private static OrderDto mapOrderToDto(Order order) {
        return OrderDto.OrderDtoBuilder.anOrderDto()
                .withOrderTrackingNumber(order.getOrderTrackingNumber())
                .withTotalPrice(order.getTotalPrice())
                .withOrderDate(order.getOrderDate())
                .withTotalQuantity(order.getTotalQuantity())
                .build();
    }


    public static OrderDto testowe1(Order order) {

        OrderDto orderDto = new OrderDto();


        List<OrderProductsDto> productDtos = new ArrayList<>();

        
        for(int i=0; i<order.getOrderItems().size(); i++){
            OrderProductsDto orderProductsDto = new OrderProductsDto();
            orderProductsDto.setProduct(mapProductToDto(order.getOrderItems().get(i).getProduct()));
            orderProductsDto.setQuantity(order.getOrderItems().get(i).getQuantity());

            productDtos.add(orderProductsDto);
        }

        orderDto.setOrderProductsDto(productDtos);
        System.out.println(new int[]{1} instanceof Object);
        return orderDto;
    }




}
