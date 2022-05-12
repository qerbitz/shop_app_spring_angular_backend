package com.shop.shop.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private String orderTrackingNumber;
    private BigDecimal totalPrice;
    private int totalQuantity;
    private Date orderDate;
    private List<OrderProductsDto> orderProductsDto;


    public static final class OrderDtoBuilder {
        private String orderTrackingNumber;
        private BigDecimal totalPrice;
        private int totalQuantity;
        private Date orderDate;
        private List<OrderProductsDto> orderProductsDto;

        private OrderDtoBuilder() {
        }

        public static OrderDtoBuilder anOrderDto() {
            return new OrderDtoBuilder();
        }

        public OrderDtoBuilder withOrderTrackingNumber(String orderTrackingNumber) {
            this.orderTrackingNumber = orderTrackingNumber;
            return this;
        }

        public OrderDtoBuilder withTotalPrice(BigDecimal totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        public OrderDtoBuilder withTotalQuantity(int totalQuantity) {
            this.totalQuantity = totalQuantity;
            return this;
        }

        public OrderDtoBuilder withOrderDate(Date orderDate) {
            this.orderDate = orderDate;
            return this;
        }

        public OrderDtoBuilder withOrderProductsDto(List<OrderProductsDto> orderProductsDto) {
            this.orderProductsDto = orderProductsDto;
            return this;
        }

        public OrderDto build() {
            OrderDto orderDto = new OrderDto();
            orderDto.setOrderTrackingNumber(orderTrackingNumber);
            orderDto.setTotalPrice(totalPrice);
            orderDto.setTotalQuantity(totalQuantity);
            orderDto.setOrderDate(orderDate);
            orderDto.setOrderProductsDto(orderProductsDto);
            return orderDto;
        }
    }
}
