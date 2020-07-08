package com.shop.shop.Service.Impl;

import com.shop.shop.Entity.User;
import com.shop.shop.Repositories.UserRepository;
import com.shop.shop.Service.Interface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean saveUser(User user, String username, String password) {
        user.setUsername(user.getUsername().trim());
        user.setPassword(user.getPassword().trim());
        //Optional<User> isNew = userRepository.findUserByUsername(username);
        //if (!isNew.isPresent()) return saveNewUser(user, username, password);
        //else {
            return false;
        //}
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

    public boolean saveNewUser(User user, String username, String password) {
        user.setUsername(username);
        user.setPassword(password);
        userRepository.save(user);
        return true;
    }
}
