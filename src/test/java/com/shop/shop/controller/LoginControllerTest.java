package com.shop.shop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.shop.entity.User;
import com.shop.shop.repositories.UserRepository;
import com.shop.shop.service.Interface.UserService;


import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.transaction.Transactional;
import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class LoginControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @AfterEach
    void clearDatabaseUsers() {
        userRepository.deleteAll();
    }

    @BeforeEach
    void clearDatabaseBefore() {
        userRepository.deleteAll();
    }


    @Test
    void shouldNotLoginSuccessfully() throws Exception {

        User user = new User();
        user.setUsername("testdsa");
        user.setPassword("testsdadsa");
        user.setEmail("tdsaaest@wp.pl");
        String jsonUser = mapper.writeValueAsString(user);

        mockMvc.perform(post("/user/login").header("Origin", "*")
                        .content(jsonUser).contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is(400))
                .andReturn();
        ;
    }

    @Test
    void shouldLoginSuccessfully() throws Exception {

        //when
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        user.setEmail("tdsaaest@wp.pl");
        userService.register(user.getUsername(), user.getPassword(), user.getEmail());

        String jsonUser = mapper.writeValueAsString(user);

        MvcResult login = mockMvc.perform(post("/user/login").header("Origin", "*")
                        .content(jsonUser).contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();

        //given
        String token = "Bearer " + login.getResponse().getHeader("Authorization");


        //then
        mockMvc.perform(get("/product/test").header("Authorization", token).header("Origin", "*"))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();


    }


    @Test
    void shouldRegisterSuccessfully() throws Exception {

        //given
        User user = new User();
        user.setUsername("notexisting");
        user.setPassword("notexisting");
        user.setEmail("notexising@gmail.com");
        String jsonUser = mapper.writeValueAsString(user);


        //when
        mockMvc.perform(post("/user/register").header("Origin", "*")
                        .content(jsonUser).contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();


        //then
        List<User> userList = userRepository.findAll();

        assertThat(userList).isNotEmpty();


    }

    @Test
    void shouldNotRegisterSuccessfully() throws Exception {

        //when
        User user = new User();
        String jsonUser = mapper.writeValueAsString(user);


        mockMvc.perform(post("/user/register").header("Origin", "*")
                        .content(jsonUser).contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is(500))
                .andReturn();

        List<User> userList = userRepository.findAll();
        assertThat(userList).hasSize(0);
    }

    @Test
    void shouldNotHaveAccessWithoutAuthorization() throws Exception {
        mockMvc.perform(get("/product/test").header("Origin", "*"))
                .andDo(print())
                .andExpect(status().is(403))
                .andReturn();
    }


    @Test
    void shouldLockAccountsAfterTooManyTry() throws Exception {

        //when
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        user.setEmail("tdsaaest@wp.pl");
        userService.register(user.getUsername(), user.getPassword(), user.getEmail());

        user.setPassword("xdd");

        String jsonUser = mapper.writeValueAsString(user);

        //when
        repeatBruteForceLoginWithBadCredentials(jsonUser);

        //then
        mockMvc.perform(post("/user/login").header("Origin", "*")
                        .content(jsonUser).contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is(401))
                .andReturn();
    }


    void repeatBruteForceLoginWithBadCredentials(String jsonUser) throws Exception {

        for (int i = 0; i < 50; i++) {
            mockMvc.perform(post("/user/login").header("Origin", "*")
                            .content(jsonUser).contentType(MediaType.APPLICATION_JSON)
                    )
                    .andDo(print())
                    .andExpect(status().is(400))
                    .andReturn();
        }
    }
}
