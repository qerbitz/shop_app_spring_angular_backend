package com.shop.shop.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    public String name;
    private Double price;
    private String image;


    public static final class ProductDtoBuilder {
        private String name;
        private Double price;
        private String image;

        private ProductDtoBuilder() {
        }

        public static ProductDtoBuilder aProductDto() {
            return new ProductDtoBuilder();
        }

        public ProductDtoBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public ProductDtoBuilder withPrice(Double price) {
            this.price = price;
            return this;
        }

        public ProductDtoBuilder withImage(String image) {
            this.image = image;
            return this;
        }



        public ProductDto build() {
            ProductDto postReadDto = new ProductDto();
            postReadDto.name = this.name;
            postReadDto.price = this.price;
            postReadDto.image = this.image;
            return postReadDto;
        }
    }
}
