package com.shop.shop.service.Impl;


import com.shop.shop.entity.User;
import com.shop.shop.entity.UserPrincipal;
import com.shop.shop.exception.*;
import com.shop.shop.repositories.UserRepository;
import com.shop.shop.service.Interface.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.RandomStringUtils;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.util.Date;

import static com.shop.shop.constant.UserImplConstant.*;
import static com.shop.shop.enumeration.Role.*;


@Service
@Transactional
@Qualifier("userDetailsService")
public class  UserServiceImpl implements UserService, UserDetailsService {


    private LoginAttemptService loginAttemptService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserRepository userRepository;
    private EmailService emailService;

    @Autowired
    public UserServiceImpl(LoginAttemptService loginAttemptService, BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository, EmailService emailService) {
        this.loginAttemptService = loginAttemptService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException(NO_USER_FOUND_BY_USERNAME + username);
        } else{
            validateLoginAttempt(user);
            user.setLastLoginDateDisplay(user.getLastLoginDate());
            user.setLastLoginDate(new Date());
            userRepository.save(user);
            UserPrincipal userPrincipal = new UserPrincipal(user);
            return userPrincipal;
        }
    }

    @Override
    public User register(String username, String password, String email) throws UserNotFoundException, EmailExistException, UsernameExistException {
        validateNewUsernameAndEmail(StringUtils.EMPTY, username, email);
        User user = new User();
       // String password = generatePassword();
        user.setUserId(generateUserId());
        user.setUsername(username);
        user.setEmail(email);
        user.setJoinDate(new Date());
        user.setPassword(encodePassword(password));
        user.setActive(true);
        user.setNotLocked(true);
        user.setRole(ROLE_USER.name());
        user.setAuthorities(ROLE_USER.getAuthorities());
        userRepository.save(user);
        //emailService.sendNewPasswordEmail(password, email);
        return user;
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public void resetPassword(String email) throws MessagingException, EmailNotFoundException {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new EmailNotFoundException(NO_USER_FOUND_BY_EMAIL + email);
        }
        String password = generatePassword();
        user.setPassword(encodePassword(password));
        userRepository.save(user);
        emailService.sendNewPasswordEmail(password, user.getEmail());
    }

    private User validateNewUsernameAndEmail(String currentUsername, String newUsername, String newEmail) throws EmailExistException, UsernameExistException, UserNotFoundException {
        User userByNewUsername = findUserByUsername(newUsername);
        User userByNewEmail = findUserByEmail(newEmail);
        if(StringUtils.isNotBlank(currentUsername)) {
            User currentUser = findUserByUsername(currentUsername);
            if(currentUser == null) {
                throw new UserNotFoundException(NO_USER_FOUND_BY_USERNAME + currentUsername);
            }
            //if(userByNewUsername != null && !currentUser.getId().equals(userByNewUsername.getId())) {
              //  throw new UsernameExistException(USERNAME_ALREADY_EXISTS);
           // }
           // if(userByNewEmail != null && !currentUser.getId().equals(userByNewEmail.getId())) {
           //     throw new EmailExistException(EMAIL_ALREADY_EXISTS);
           // }
            return currentUser;
        } else {
            if(userByNewUsername != null) {
                throw new UsernameExistException(USERNAME_ALREADY_EXISTS);
            }
            if(userByNewEmail != null) {
                throw new EmailExistException(EMAIL_ALREADY_EXISTS);
            }
            return null;
        }
    }

    private String encodePassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    private String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(10);
    }

    private String generateUserId() {
        return RandomStringUtils.randomNumeric(10);
    }

    private void validateLoginAttempt(User user) {
        if(user.isNotLocked()) {
            if(loginAttemptService.hasExceededMaxAttempts(user.getUsername())) {
                user.setNotLocked(false);
            } else {
                user.setNotLocked(true);
            }
        } else {
            loginAttemptService.evictUserFromLoginAttemptCache(user.getUsername());
        }
    }
}
