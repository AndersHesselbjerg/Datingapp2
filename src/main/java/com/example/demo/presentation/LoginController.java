package com.example.demo.presentation;

import com.example.demo.data.UserMapper;
import com.example.demo.domain.User;

import javax.security.auth.login.LoginException;

public class LoginController {
    private final UserMapper userMapper;

    public LoginController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public User login(String email, String password) throws LoginException {
        User user = userMapper.getUserByMail(email);
        if (user.getPassword().equals(password)) {
            return user;
        } else {
            throw new LoginException();
        }
    }
}

