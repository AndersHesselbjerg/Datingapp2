package com.example.demo.services.JDBC;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
@Scope("Singleton")
public class JDBCHandler {

    private final JDBCReader jdbcReader;
    private final JDBCWriter jdbcWriter;
    private Connection connection;

    JDBCHandler(JDBCReader jdbcReader, JDBCWriter jdbcWriter) {
        this.jdbcReader = jdbcReader;
        this.jdbcWriter = jdbcWriter;
    }

    public boolean setConnection(String url) {
        try {
            connection = DriverManager.getConnection(url);
            return true;
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            return false;
        }
    }
}
