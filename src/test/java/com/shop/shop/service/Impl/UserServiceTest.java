package com.shop.shop.service.Impl;

import static com.shop.shop.constant.UserImplConstant.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;

import com.shop.shop.entity.User;
import com.shop.shop.exception.EmailExistException;
import com.shop.shop.exception.UserNotFoundException;
import com.shop.shop.exception.UsernameExistException;
import com.shop.shop.repositories.UserRepository;

import com.shop.shop.service.Interface.UserService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import javax.mail.MessagingException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;


@ContextConfiguration(classes = {UserServiceImpl.class, BCryptPasswordEncoder.class})
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
class UserServiceTest {


    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @MockBean
    private EmailService emailService;

    @MockBean
    private LoginAttemptService loginAttemptService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService underTest;

    @Autowired
    private UserServiceImpl underTestImpl;


    @Test
    void shouldRegisterSuccessfully() throws UserNotFoundException, EmailExistException, MessagingException, UsernameExistException {
        //given
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setPassword("iloveyou");
        user.setUsername("janedoe");

        //when
        underTest.register(user.getUsername(), user.getPassword(), user.getEmail());


        given(userRepository.findUserByEmail(anyString()))
                .willReturn(user);

        //then

        ArgumentCaptor<User> userArgumentCaptor =
                ArgumentCaptor.forClass(User.class);

        verify(userRepository)
                .save(userArgumentCaptor.capture());

        User capturedUser = userArgumentCaptor.getValue();

        assertThat(capturedUser.getUsername()).isEqualTo(user.getUsername());


    }

    @Test
    void willLoadSuccessfullyUserByUsername() {

        //given

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setPassword("iloveyou");
        user.setUsername("janedoe");

       // given(userRepository.findUserByUsername(anyString()))
         //       .willReturn(user);

        assertThatThrownBy(() -> underTestImpl.loadUserByUsername(user.getUsername()))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessageContaining(NO_USER_FOUND_BY_USERNAME);

        //then
        verify(userRepository, never()).save(any());

    }

    @Test
    void willThrowWhenEmailIsTaken() {

        //given

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setPassword("iloveyou");
        user.setUsername("janedoe");

        given(userRepository.findUserByEmail(anyString()))
                .willReturn(user);

        assertThatThrownBy(() -> underTest.register(user.getUsername(), user.getPassword(), user.getEmail()))
                .isInstanceOf(EmailExistException.class)
                .hasMessageContaining(EMAIL_ALREADY_EXISTS);


        //then

        verify(userRepository, never()).save(any());

    }

    @Test
    void willThrowWhenUsernameExist() {

        //given

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setPassword("iloveyou");
        user.setUsername("janedoe");

        given(userRepository.findUserByUsername(anyString()))
                .willReturn(user);

        assertThatThrownBy(() -> underTest.register(user.getUsername(), user.getPassword(), user.getEmail()))
                .isInstanceOf(UsernameExistException.class)
                .hasMessageContaining(USERNAME_ALREADY_EXISTS);

        verify(userRepository, never()).save(any());

    }
}

