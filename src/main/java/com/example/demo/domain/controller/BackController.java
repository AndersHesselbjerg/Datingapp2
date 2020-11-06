package com.example.demo.domain.controller;

import com.example.demo.data.jdbc.JDBCHandler;
import com.example.demo.domain.user.UserHandler;
import com.example.demo.domain.user.UserList;

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
}
