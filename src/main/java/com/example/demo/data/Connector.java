package com.example.demo.data;

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
public class Connector {
    private Connection connection;

    public Connection setConnection() {
        try (InputStream input = new FileInputStream("src/main/resources/application.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            String url = properties.getProperty("url");
            String user = properties.getProperty("user");
            String pass = properties.getProperty("pass");
            connection = DriverManager.getConnection(url, user, pass);
            return connection;
        } catch (SQLException e) {
            throw new NullPointerException("A SQL Exception was thrown");
        } catch (FileNotFoundException e) {
            throw new NullPointerException("A FileNotFound Exception was thrown");
        } catch (IOException e) {
            throw new NullPointerException("A IO Exception was thrown");
        }
    }
}
