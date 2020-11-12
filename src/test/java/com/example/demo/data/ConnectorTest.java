package com.example.demo.data;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ConnectorTest {

    @Test
    void getConnection() {
        Connector connector = new Connector();
        Connection connection = connector.getConnection();
        /*
        String url = "jdbc:mysql://167.99.255.45:3306/mydb";
        String user = "grassroot";
        String pass = "grassroot_2020_WORK";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, pass);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        */
        assertNotNull(connection);
    }
}