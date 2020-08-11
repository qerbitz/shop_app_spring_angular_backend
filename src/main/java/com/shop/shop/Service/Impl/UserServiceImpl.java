package com.shop.shop.Service.Impl;

import com.shop.shop.Entity.Authorities;
import com.shop.shop.Entity.Cart;
import com.shop.shop.Entity.User;
import com.shop.shop.Repositories.AuthoritiesRepository;
import com.shop.shop.Repositories.UserRepository;
import com.shop.shop.Service.Interface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthoritiesRepository authoritiesRepository;
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void saveUser(User user) {
        StringBuilder password = new StringBuilder("{bcrypt}").append(passwordEncoder.encode(user.getPassword()));
        user.setPassword(password.toString());
        user.setEnabled(1);

        Cart cart = new Cart();
        cart.setId_cart(2);
        user.setCart(cart);


        Authorities authorities = new Authorities();
        authorities.setUsername(user);
        authorities.setAuthority("Customer");


        userRepository.save(user);
        authoritiesRepository.save(authorities);
    }

    @Override
    public boolean checkUniqueness(String username) {
        Optional<User> xdd = userRepository.findByUsername(username);
        if (xdd.isPresent()) {
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.getOne(username);
    }

}
