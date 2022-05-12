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

    private int id_product;
    public String name;
    private Double price;
    private String image;


    public static final class ProductDtoBuilder {
        private int id_product;
        public String name;
        private Double price;
        private String image;

        private ProductDtoBuilder() {
        }

        public static ProductDtoBuilder aProductDto() {
            return new ProductDtoBuilder();
        }

        public ProductDtoBuilder withId(int id) {
            this.id_product = id;
            return this;
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
            ProductDto productDto = new ProductDto();
            productDto.setId_product(this.id_product);
            productDto.setName(this.name);
            productDto.setPrice(this.price);
            productDto.setImage(this.image);
            return productDto;
        }
    }
}
