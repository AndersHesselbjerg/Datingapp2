package com.example.demo.presentation;

import com.example.demo.data.UserMapper;
import com.example.demo.domain.User;
import com.example.demo.domain.UserList;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("Singleton")
public class UserController {
    private final UserMapper userMapper;

    UserController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public UserList getAllUsers() {
        return userMapper.getAllUsers();
    }

    public UserList getUsers(int limit, int start_row) {
        return userMapper.getUsers(limit, start_row);
    }

    public User getUser(String username) {
        return userMapper.getUser(username);
    }

    public void updateUser(User user) {
        userMapper.updateUser(user);
    }
}
