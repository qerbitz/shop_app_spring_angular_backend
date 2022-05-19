package com.shop.shop.service.Impl;


import com.shop.shop.entity.Order;
import com.shop.shop.entity.User;
import com.shop.shop.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl underTest;

    @BeforeEach
    void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
    }



    @Test
    void shouldGetListOfAllOrdersByUser(){

        Order order1 = new Order();
        Order order2 = new Order();
        List<Order> orderList = new ArrayList<>();
        orderList.add(order1);
        orderList.add(order2);

        //when
        when(underTest.getAllOrdersByUser(any())).thenReturn(orderList);

        //then
        List<Order> orderList2 = new ArrayList<>();
        //verify(orderRepository).getAllByUserOrderByOrderDateDesc((User) any());

    }

    @Test
    void shouldGetSingleOrdersByUser(){

        //when
        underTest.getOrderById(anyString());

        //then
        verify(orderRepository).getOrderByOrderTrackingNumber(anyString());

    }

    @Test
    void testConvertDate() {
        Date actualDate = underTest.convertDate(LocalDate.ofEpochDay(1L));
        assertEquals("1970-01-02", (new SimpleDateFormat("yyyy-MM-dd")).format(actualDate));
    }
}
