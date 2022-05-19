package com.shop.shop.service.Impl;

import com.shop.shop.Dto.ProductDto;
import com.shop.shop.entity.Product;
import com.shop.shop.exception.EmailExistException;
import com.shop.shop.repositories.ProductRepository;
import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static com.shop.shop.constant.UserImplConstant.EMAIL_ALREADY_EXISTS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl underTest;

    @BeforeEach
    void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldGetSingleProduct() throws NotFoundException {

        Product product = new Product();
        product.setId_product(1);
        product.setName("Name");

        Optional<Product> productOptional = Optional.of(product);

        when(productRepository.findById(any())).thenReturn(productOptional);

        ProductDto actualProductById = underTest.getProductById(1);


        assertEquals(1, actualProductById.getId_product());
        assertEquals("Name", actualProductById.getName());

    }

    @Test
    void shouldThrowErorWhenProductNotExist() {


        //when
        when(productRepository.findById(any())).thenReturn(Optional.empty());


        //then
        assertThatThrownBy(() -> underTest.getProductById(1))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("No product found by id: ");

    }
}
