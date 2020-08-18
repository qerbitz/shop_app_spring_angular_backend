package com.shop.shop.Service.Interface;

import com.shop.shop.Entity.User;

public interface UserService {

    void saveUser(User user);

    void updateUser(User user);

    boolean checkUniqueness(String username);

    User getUserByUsername(String username);

}
