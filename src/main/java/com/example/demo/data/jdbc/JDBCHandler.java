package com.example.demo.data.jdbc;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
@Scope("Singleton")
public class JDBCHandler {
    private JDBCConnector jdbcConnector;

    JDBCHandler(JDBCConnector jdbcConnector) {
        this.jdbcConnector = jdbcConnector;
        jdbcConnector.setConnection();
    }

    public ResultSet getAllUsers() {
        String statement = "SELECT * FROM mydb.users;";
        return jdbcConnector.query(statement);
    }

    public ResultSet getUsers(int limit, int start_row) {
        String statement = "SELECT * FROM mydb.users LIMIT " + limit + "," + start_row + ";";
        return jdbcConnector.query(statement);
    }

    public ResultSet getUser(int id) {
        String statement = "SELECT * FROM mydb.users WHERE ID LIKE " + id + ";";
        return jdbcConnector.query(statement);
    }

    public ResultSet getUser(String username) {
        String statement = "SELECT * FROM mydb.users WHERE username LIKE \"" + username + "\";";
        return jdbcConnector.query(statement);
    }
}
