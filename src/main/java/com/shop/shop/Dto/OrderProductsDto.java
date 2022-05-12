package com.shop.shop.Dto;

import com.shop.shop.entity.OrderItem;
import com.shop.shop.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductsDto {

    private ProductDto product;
    private int quantity;


    public static final class OrderProductsDtoBuilder {
        private ProductDto product;
        private int quantity;

        private OrderProductsDtoBuilder() {
        }

        public static OrderProductsDtoBuilder anOrderProductsDto() {
            return new OrderProductsDtoBuilder();
        }

        public OrderProductsDtoBuilder withProduct(ProductDto product) {
            this.product = product;
            return this;
        }

        public OrderProductsDtoBuilder withQuantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public OrderProductsDto build() {
            OrderProductsDto orderProductsDto = new OrderProductsDto();
            orderProductsDto.setProduct(product);
            orderProductsDto.setQuantity(quantity);
            return orderProductsDto;
        }
    }
}
