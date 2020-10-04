package com.shop.shop.service;

import com.shop.shop.Entity.Order;
import com.shop.shop.Entity.User;
import com.shop.shop.Repositories.OrderRepository;
import com.shop.shop.Service.Impl.OrderServiceImpl;
import com.shop.shop.Service.Impl.UserServiceImpl;
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
public class OrderServiceImplTests {

    @Mock
    OrderRepository orderRepository;
    @Mock
    UserServiceImpl userService;

    @InjectMocks
    OrderServiceImpl orderService;

    @Test
    public void getAllOrdersByUser(){

        User user = userService.getUserByUsername("Testowy532");
        // given
        given(orderRepository.getAllByUser(user)).willReturn(prepareData());
        //when
        List<Order> listOfOrders = orderService.getAllOrdersByUser(user);
        // then
        assertThat(listOfOrders, hasSize(5));

    }


    private List<Order> prepareData() {
        Order order1 = new Order();
        Order order2 = new Order();
        Order order3 = new Order();
        Order order4 = new Order();;
        Order order5 = new Order();
        return Arrays.asList(order1, order2, order3, order4, order5);
    }
}
