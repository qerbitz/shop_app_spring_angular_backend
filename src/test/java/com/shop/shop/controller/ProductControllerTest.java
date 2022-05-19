package com.shop.shop.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;



    @Test
    void shouldGetAllProducts() throws Exception {
        mockMvc.perform(get("/product/productList").header("Origin", "*")
                        .param("page", "1")
                        .param("size","1")
                        .param("sort_option", "0")
                )
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();
    }

    @Test
    void shouldGetAllProductsFiltered() throws Exception {
        mockMvc.perform(get("/product/filter").header("Origin", "*")
                        .param("page", "0")
                        .param("size","1")
                        .param("sort_option", "0")
                        .param("category","0")
                        .param("gender","")
                        .param("price","0-100")
                )
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();
    }

    @Test
    void shouldGetSingleProduct() throws Exception{
        mockMvc.perform(get("/product/viewProduct/1").header("Origin", "*")
                )
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();
    }

    @Test
    void shouldGetAllProductsBySale() throws Exception {
        mockMvc.perform(get("/product/BySaleProductList").header("Origin", "*")
                )
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();
    }

    @Test
    void shouldGetAllProductsByKeyword() throws Exception {
        mockMvc.perform(get("/product/productList?page=1&size=1&theKeyword=xd&sort_option=0").header("Origin", "*"))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();
    }

    @Test
    void shouldGetAllCategories() throws Exception {

        mockMvc.perform(get("/product/categoryList").header("Origin", "*"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(content().contentType("application/json"))
                .andReturn();
    }

}
