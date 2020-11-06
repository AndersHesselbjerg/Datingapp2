package com.example.demo.domain.JDBC;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

@Component
@Scope("Singleton")
public class JDBCEntity {
    private Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean setConnection() {
        try (InputStream input = new FileInputStream("src/main/resources/application.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            String url = properties.getProperty("url");
            String user = properties.getProperty("user");
            String pass = properties.getProperty("pass");
            connection = DriverManager.getConnection(url, user, pass);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ResultSet query(String statement) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (SQLException sqlException) {
            throw new NullPointerException("Connection to DB could not be made...");
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
