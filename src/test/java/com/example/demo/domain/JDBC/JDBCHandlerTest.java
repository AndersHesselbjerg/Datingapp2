package com.example.demo.domain.JDBC;

import com.mysql.cj.jdbc.result.ResultSetImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JDBCHandlerTest {

    private String url;
    private String user;
    private String pass;

    JDBCHandlerTest() {
        try (InputStream input = new FileInputStream("src/main/resources/application.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            this.url = properties.getProperty("url");
            this.user = properties.getProperty("user");
            this.pass = properties.getProperty("pass");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void setConnection() {
        JDBCHandler jdbcHandler = new JDBCHandler();
        boolean test = jdbcHandler.setConnection();
        assertEquals(true, test);
    }

    @Test
    void getUsers() {
        JDBCReader jdbcReader = new JDBCReader();
        int[] ids = {0,1};
        try {
            Connection connection = DriverManager.getConnection(url, user, pass);
            jdbcReader.setConnection(connection);
            ResultSet resultSet = jdbcReader.getUsers(ids);
            assertEquals(ResultSetImpl.class, resultSet.getClass());
            // Important detail when querying from dbms. Always get next item!
            if (resultSet.next()) {
                String result = resultSet.getString("id");
                assertEquals("1", result);
                result = resultSet.getString("username");
                assertEquals("Akila", result);
                result = resultSet.getString("first_name");
                assertEquals("Rasmus", result);
                result = resultSet.getString("last_name");
                assertEquals("Bilgård", result);
            } else {
                // Call an assertion that will fail
                assertEquals(1,0);
            }
            // assertEquals(0,resultSet.getObject("id"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
