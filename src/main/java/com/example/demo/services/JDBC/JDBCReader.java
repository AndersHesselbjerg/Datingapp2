package com.example.demo.services.JDBC;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;

@Component
@Scope("Singleton")
public class JDBCReader {
    private Connection connection;
    private PreparedStatement preparedStatement;

    public void setConnection(Connection connection) { this.connection = connection; }

    public void getUsers(int[] ids) { /* return ResultSet */ }

    public void getChat(int id) { /* return ResultSet */ }
}
