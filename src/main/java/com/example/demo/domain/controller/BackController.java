package com.example.demo.domain.controller;

import com.example.demo.data.jdbc.JDBCHandler;
import com.example.demo.domain.user.UserHandler;
import com.example.demo.domain.user.UserList;
import com.example.demo.models.User;

import java.sql.ResultSet;

public class BackController {
    UserHandler userHandler;
    JDBCHandler jdbcHandler;

    BackController(UserHandler userHandler, JDBCHandler jdbcHandler) {
        this.userHandler = userHandler;
        this.jdbcHandler = jdbcHandler;
    }

    public UserList getAllUsers() {
        ResultSet resultSet = jdbcHandler.getAllUsers();
        userHandler.buildUsers(resultSet);
        UserList users = userHandler.fetchUsers();
        return users;
    }

    public User getUser(String username) {
        ResultSet resultSet = jdbcHandler.getUser(username);
        userHandler.buildUsers(resultSet);
        UserList users = userHandler.fetchUsers();
        return users.get(0);
    }

    public User getUserByID(int id) {
        ResultSet resultSet = jdbcHandler.getUser(id);
        userHandler.buildUsers(resultSet);
        UserList users = userHandler.fetchUsers();
        return users.get(0);
    }
}
