package com.shop.shop.controller;

import com.shop.shop.service.Interface.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
        mockMvc.perform(get("/product/productList?page=1&size=1"))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();
    }

    @Test
    void shouldGetAllProductsByKeyword() throws Exception {
        mockMvc.perform(get("/product/productList?page=1&size=1&theKeyword=xd"))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();
    }

    @Test
    void shouldGetAllCategories() throws Exception {

        mockMvc.perform(get("/product/categoryList"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string("[]"))
                .andReturn();
    }

}
