package com.example.demo.services.JDBC;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@Scope("Singleton")
public class JDBCReader {
    private Connection connection;
    private PreparedStatement preparedStatement;

    public void setConnection(Connection connection) { this.connection = connection; }

    public ResultSet getUsers(int[] ids) {
        String statement = "SELECT * FROM mydb.users;";
        try {
            preparedStatement = connection.prepareStatement(statement);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (SQLException sqlException) {
            throw new NullPointerException();
        }
    }

    public void getChat(int id) { /* return ResultSet */ }
}
