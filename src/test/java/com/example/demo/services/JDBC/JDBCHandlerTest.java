package com.example.demo.services.JDBC;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JDBCHandlerTest {

    JDBCReader jdbcReader = new JDBCReader();
    JDBCWriter jdbcWriter = new JDBCWriter();
    JDBCHandler jdbcHandler = new JDBCHandler(jdbcReader, jdbcWriter);

    @Test
    void setConnection() {
        String tmp = "jdbc:mysql://localhost:3306";
        jdbcHandler.setConnection(tmp);
    }
}