package com.shop.shop.service;

import com.shop.shop.Entity.User;
import com.shop.shop.Repositories.UserRepository;
import com.shop.shop.Service.Impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class UsersServiceImplTests {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;


    @Test
    public void checkUserUniquess(){

        //given
        //given(userRepository.findById("batonik2")).willReturn(java.util.Optional.of(new User("batonik2","zaq1@WSX")));
    }


}
