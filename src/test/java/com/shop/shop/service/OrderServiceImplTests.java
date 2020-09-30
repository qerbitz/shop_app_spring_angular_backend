package com.shop.shop.service;

import com.shop.shop.Repositories.OrderRepository;
import com.shop.shop.Service.Impl.OrderServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceImplTests {

    @Mock
    OrderRepository orderRepository;

    @InjectMocks
    OrderServiceImpl orderService;

    @Test
    public void getAllOrdersByUser(){
        // given

    }
}
