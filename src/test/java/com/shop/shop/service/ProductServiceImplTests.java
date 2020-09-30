package com.shop.shop.service;

import com.shop.shop.Entity.Product;
import com.shop.shop.Repositories.ProductRepository;
import com.shop.shop.Service.Impl.ProductServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceImplTests {

    @Mock
    ProductRepository productsRepository;

    @InjectMocks
    ProductServiceImpl productsService;

    @Test
    public void getAllProducts() {
        // given
        given(productsRepository.findAll()).willReturn(prepareData());
        // when
        List<Product> listOfProducts = productsService.getListOfProducts();
        // then
        assertThat(listOfProducts, hasSize(5));
    }

    @Test
    public void updateProductQuantity() {
         //given
        Product product = new Product(1, "Skarpetki", 20);
        // when
        productsService.changeQuantityOfProduct(product,1);
        // then
        assertThat(product.getQuantity(), is(equalTo(19)));
    }

    private List<Product> prepareData() {
        Product product1 = new Product();
        Product product2 = new Product();
        Product product3 = new Product();
        Product product4 = new Product();
        Product product5 = new Product();
        return Arrays.asList(product1, product2, product3, product4, product5);
    }
}
