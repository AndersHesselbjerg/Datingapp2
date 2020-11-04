package com.example.demo.services.JDBC;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.print.AttributeException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@Scope("Singleton")
public class JDBCHandler {
    private JDBCReader jdbcReader;
    private JDBCWriter jdbcWriter;
    private Connection connection;
    private String url = "jdbc:mysql://localhost:3306/mydb?serverTimezone=UTC";
    private String user = "DB";
    private String pass = "333";

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
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            return false;
        }
    }

    public ResultSet getUsers(int[] ids) {
        return jdbcReader.getUsers(ids);
    }

    public void getChat(int id) {/* return ResultSet */}
}
