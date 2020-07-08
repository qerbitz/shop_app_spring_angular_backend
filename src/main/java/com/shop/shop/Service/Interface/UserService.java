package com.shop.shop.Service.Interface;

import com.shop.shop.Entity.User;

public interface UserService {

    boolean saveUser(User user, String username, String password);

    boolean checkUniqueness(String username);
}
