package com.example.demo.domain.JDBC;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

@Component
@Scope("Singleton")
public class JDBCHandler {
    private JDBCReader jdbcReader;
    private JDBCWriter jdbcWriter;
    private Connection connection;
    private String url;
    private String user;
    private String pass;

    JDBCHandler() {
        try (InputStream input = new FileInputStream("src/main/resources/application.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            url = properties.getProperty("url");
            user = properties.getProperty("user");
            pass = properties.getProperty("pass");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Autowired
    public void setJdbcReader(JDBCReader jdbcReader) {
        this.jdbcReader = jdbcReader;
        jdbcReader.setConnection(connection);
    }

    @Autowired
    public void setJdbcWriter(JDBCWriter jdbcWriter) {
        this.jdbcWriter = jdbcWriter;
        jdbcWriter.setConnection(connection);
    }

    public boolean setConnection() {
        try {
            connection = DriverManager.getConnection(url, user, pass);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ResultSet getAllUsers() {
        String statement = "SELECT * FROM mydb.users;";
        return jdbcReader.query(statement);
    }

    public ResultSet getUser() {
        String statement = "SELECT * FROM mydb.users;";
        return jdbcReader.query(statement);
    }
}
