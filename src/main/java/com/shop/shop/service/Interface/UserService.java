package com.shop.shop.service.Interface;


import com.shop.shop.entity.User;
import com.shop.shop.exception.*;

import javax.mail.MessagingException;

public interface UserService {

    User register(String username, String password, String email) throws UserNotFoundException, UsernameExistException, EmailExistException, MessagingException;

    User findUserByUsername(String username);

    User findUserByEmail(String email);

    void resetPassword(String email) throws MessagingException, EmailNotFoundException;


}
