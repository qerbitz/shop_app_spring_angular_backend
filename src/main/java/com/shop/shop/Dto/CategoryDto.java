package com.shop.shop.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {


    private int id_category;
    public String name;


    public static final class CategoryDtoBuilder {
        private int id_category;
        private String name;

        private CategoryDtoBuilder() {
        }

        public static CategoryDto.CategoryDtoBuilder aCategoryDto() {
            return new CategoryDto.CategoryDtoBuilder();
        }

        public CategoryDto.CategoryDtoBuilder withId(int id_category) {
            this.id_category = id_category;
            return this;
        }

        public CategoryDto.CategoryDtoBuilder withName(String name) {
            this.name = name;
            return this;
        }



        public CategoryDto build() {
            CategoryDto categoryReadDto = new CategoryDto();
            categoryReadDto.id_category = this.id_category;
            categoryReadDto.name = this.name;
            return categoryReadDto;
        }
    }
}
