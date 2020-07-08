package com.shop.shop.Service.Impl;

import com.shop.shop.Entity.User;
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
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void saveUser(User user) {
        user.setId_user(11);
        user.setUsername(user.getUsername());
        user.setE_mail(user.getE_mail());

        StringBuilder password = new StringBuilder("{bcrypt}").append(passwordEncoder.encode(user.getPassword()));
        user.setPassword(password.toString());

        userRepository.save(user);
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

}
