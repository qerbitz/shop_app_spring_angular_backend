package com.shop.shop.service.Impl;

import static com.shop.shop.constant.UserImplConstant.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

import com.shop.shop.entity.User;
import com.shop.shop.entity.UserPrincipal;
import com.shop.shop.exception.EmailExistException;
import com.shop.shop.exception.UserNotFoundException;
import com.shop.shop.exception.UsernameExistException;
import com.shop.shop.repositories.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import javax.mail.MessagingException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


class UserServiceTest {

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl underTest;

    @Mock
    private LoginAttemptService loginAttemptService;

    @BeforeEach
    void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void willThrowWhenNoUserWasFound() {

        assertThatThrownBy(() -> underTest.loadUserByUsername("janedoe"))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessageContaining(NO_USER_FOUND_BY_USERNAME);

    }

    @Test
    void willSuccessfullyLoadUserByUsername() {

        //given
        User user = new User();
        user.setUsername("janedoe");

        //when
        when(userRepository.findUserByUsername(anyString())).thenReturn(user);

        UserDetails userDetails = underTest.loadUserByUsername(anyString());

        assertNotNull(userDetails);
        assertEquals("janedoe", userDetails.getUsername());
    }


    @Test
    void willFindSuccessfullyUserByUsername() {

        //given
        User user = new User();
        user.setUsername("janedoe");

        //when
        when(userRepository.findUserByUsername(anyString())).thenReturn(user);

        User user2 = underTest.findUserByUsername(anyString());

        assertNotNull(user2);
        assertEquals("janedoe",user2.getUsername());
    }

    @Test
    void willNotFoundSuccessfullyUserByUsername() {


        //when
        when(userRepository.findUserByUsername(anyString())).thenReturn(null);

        User user2 = underTest.findUserByUsername("test@test.pl");

        assertNull(user2);
    }

    @Test
    void shouldRegisterSuccessfully() throws UserNotFoundException, EmailExistException, MessagingException, UsernameExistException {
        //given
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setPassword("iloveyou");
        user.setUsername("janedoe");

        //when
        underTest.register(user.getUsername(), user.getPassword(), user.getEmail());


        given(userRepository.findUserByEmail(anyString())).willReturn(user);

        //then

        ArgumentCaptor<User> userArgumentCaptor =
                ArgumentCaptor.forClass(User.class);

        verify(userRepository)
                .save(userArgumentCaptor.capture());

        User capturedUser = userArgumentCaptor.getValue();

        assertThat(capturedUser.getUsername()).isEqualTo(user.getUsername());

        verify(userRepository).save(any());
    }

    @Test
    void willThrowWhenEmailIsTaken() {

        //given

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setPassword("iloveyou");
        user.setUsername("janedoe");



       when(userRepository.findUserByEmail(anyString()))
                .thenReturn(user);

        assertThatThrownBy(() -> underTest.register(user.getUsername(), user.getPassword(), user.getEmail()))
                .isInstanceOf(EmailExistException.class)
                .hasMessageContaining(EMAIL_ALREADY_EXISTS);

        verify(userRepository, never()).save(any());


    }

    @Test
    void willThrowWhenUsernameExist() {


        //given

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setPassword("iloveyou");
        user.setUsername("janedoe");


        when(userRepository.findUserByUsername(anyString()))
                .thenReturn(user);

        assertThatThrownBy(() -> underTest.register(user.getUsername(), user.getPassword(), user.getEmail()))
                .isInstanceOf(UsernameExistException.class)
                .hasMessageContaining(USERNAME_ALREADY_EXISTS);

        verify(userRepository, never()).save(any());

    }


}