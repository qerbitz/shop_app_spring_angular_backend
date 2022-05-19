package com.shop.shop.repository;


import com.shop.shop.entity.User;
import com.shop.shop.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class UserRepositoryTest {


    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void itShouldFindUserByUsername(){
        // given
        User user = new User();
        user.setUsername("test552225");
        user.setPassword("test552q225");

        userRepository.save(user);

        // when
        boolean expected = userRepository.findUserByUsername(user.getUsername())!=null;

        // then
        assertThat(expected).isTrue();
    }

    @Test
    void itShouldNotFindUserByUsername(){
        // given
        String username = "testNotFound";


        // when
        boolean expected = userRepository.findUserByUsername(username)==null;

        // then
        assertThat(expected).isTrue();
    }

    @Test
    void itShouldFindUserByEmail(){
        // given
        User user = new User();
        user.setEmail("test@wp.pl");

        userRepository.save(user);

        // when
        boolean expected = userRepository.findUserByEmail(user.getEmail())!=null;

        // then
        assertThat(expected).isTrue();
    }

    @Test
    void itShouldNotFindUserByEmail(){

        // when
        boolean expected = userRepository.findUserByEmail("testowawa@wp.pl")==null;

        // then
        assertThat(expected).isTrue();
    }

}
