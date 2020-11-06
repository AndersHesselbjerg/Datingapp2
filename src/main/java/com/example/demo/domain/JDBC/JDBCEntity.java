package com.example.demo.domain.JDBC;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@Scope("Singleton")
public class JDBCEntity {
    private Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public ResultSet query(String statement) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (SQLException sqlException) {
            throw new NullPointerException();
        }
    }

    public void alter(String statement) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            throw new NullPointerException();
        }
    }
}
