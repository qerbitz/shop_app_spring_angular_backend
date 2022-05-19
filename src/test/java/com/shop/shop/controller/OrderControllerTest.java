package com.shop.shop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.shop.Dto.Purchase;
import com.shop.shop.entity.Address;
import com.shop.shop.entity.Order;
import com.shop.shop.entity.OrderItem;
import com.shop.shop.entity.User;
import com.shop.shop.repositories.OrderRepository;
import com.shop.shop.repositories.UserRepository;
import com.shop.shop.service.Impl.OrderServiceImpl;
import com.shop.shop.service.Interface.OrderService;
import com.shop.shop.service.Interface.UserService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;



import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @AfterEach
    void clearDatabaseOrders(){
        orderRepository.deleteAll();
    }

    @Test
    void shouldGetAllOrdersByUser() throws Exception {

        User user = new User();
        user.setUsername("test332");
        user.setPassword("test321");
        user.setEmail("tdsaaes21t@wp.pl");
        userService.register(user.getUsername(), user.getPassword(), user.getEmail());

        String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJhdWQiOiJVc2VyIE1hbmFnZW1lbnQgUG9ydGFsIiwic3ViIjoidGVzdDE1NSIsImlzcyI6IkdldCBBcnJheXMsIExMQyIsImV4cCI6MTY1MzI1NTIxNywiaWF0IjoxNjUyODIzMjE3LCJhdXRob3JpdGllcyI6WyJ1c2VyOnJlYWQiXX0.jWXQEwT_Wn_DH1JSfYUWaq2NDABPJsR_zjToWJiO7cgUfx-XjWudN0x9C16q2PfLgw_jkW8S2IogXiQp3m5JrA";

        //then
        mockMvc.perform(get("/order/allOrders").header("Origin", "*").header("Authorization", token)
                        .param("username", "test")
                )
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();
    }

    @Test
    void shouldNotGetAllOrdersByUser() throws Exception {


        //then
        mockMvc.perform(get("/order/allOrders").header("Origin", "*")
                        .param("username", "test")
                )
                .andDo(print())
                .andExpect(status().is(403))
                .andReturn();
    }

    @Test
    void shouldNotGetOrderDetails() throws Exception {


        //then
        mockMvc.perform(get("/order/orderDetails").header("Origin", "*")
                        .param("order_id", "testowe")
                )
                .andDo(print())
                .andExpect(status().is(403))
                .andReturn();
    }

    @Test
    void shouldGetOrderDetails() throws Exception {

        User user = new User();
        user.setUsername("test155");
        user.setPassword("test155");
        user.setEmail("tdsaaes12t2@wp.pl");
        user.setFirstName("name");
        user.setLastName("lastname");

        userService.register(user.getUsername(), user.getPassword(), user.getEmail());


        Purchase purchase = new Purchase();

        Order order1 = new Order();
        order1.setId(123L);
        order1.setOrderItems(new ArrayList<>());
        order1.setUser(user);

        Address address5 = new Address();

        purchase.setOrderItems(new HashSet<>());
        purchase.setOrder(order1);
        purchase.setAddress(address5);
        purchase.setUser(user);


        String order_id = orderService.addNewOrder(purchase);


        String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJhdWQiOiJVc2VyIE1hbmFnZW1lbnQgUG9ydGFsIiwic3ViIjoidGVzdDE1NSIsImlzcyI6IkdldCBBcnJheXMsIExMQyIsImV4cCI6MTY1MzI1NTIxNywiaWF0IjoxNjUyODIzMjE3LCJhdXRob3JpdGllcyI6WyJ1c2VyOnJlYWQiXX0.jWXQEwT_Wn_DH1JSfYUWaq2NDABPJsR_zjToWJiO7cgUfx-XjWudN0x9C16q2PfLgw_jkW8S2IogXiQp3m5JrA";

        //then
        mockMvc.perform(get("/order/orderDetails").header("Origin", "*").header("Authorization", token)
                        .param("order_id", order_id)
                )
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();
    }


    @Test
    void shouldPlaceOrderSuccessfully() throws Exception {

        //user
        User user = new User();
        user.setUsername("test332232");
        user.setPassword("test322111");
        user.setEmail("tdsaaes2212t@wp.pl");
        user.setFirstName("name");
        user.setLastName("lastname");

        userService.register(user.getUsername(), user.getPassword(), user.getEmail());

        String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJhdWQiOiJVc2VyIE1hbmFnZW1lbnQgUG9ydGFsIiwic3ViIjoidGVzdDE1NSIsImlzcyI6IkdldCBBcnJheXMsIExMQyIsImV4cCI6MTY1MzI1NTIxNywiaWF0IjoxNjUyODIzMjE3LCJhdXRob3JpdGllcyI6WyJ1c2VyOnJlYWQiXX0.jWXQEwT_Wn_DH1JSfYUWaq2NDABPJsR_zjToWJiO7cgUfx-XjWudN0x9C16q2PfLgw_jkW8S2IogXiQp3m5JrA";

        Purchase purchase = new Purchase();


        Order order1 = new Order();
        order1.setId(123L);
        order1.setOrderItems(new ArrayList<>());
        order1.setUser(user);


        Address address5 = new Address();

        purchase.setOrderItems(new HashSet<>());
        purchase.setOrder(order1);
        purchase.setAddress(address5);
        purchase.setUser(user);
        String purchaseObject = mapper.writeValueAsString(purchase);

        //then
        mockMvc.perform(post("/order/placeOrder").header("Origin", "*").header("Authorization", token)
                        .content(purchaseObject).contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();
    }


}
