package com.example.demo.services.JDBC;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JDBCHandlerTest {

    JDBCHandler jdbcHandler = new JDBCHandler();

    @Test
    void setConnection() {
        boolean test = jdbcHandler.setConnection();
        assertEquals(true, test);
    }
}
