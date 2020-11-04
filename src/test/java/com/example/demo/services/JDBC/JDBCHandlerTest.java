package com.example.demo.services.JDBC;

import com.mysql.cj.jdbc.result.ResultSetImpl;
import com.mysql.cj.protocol.Resultset;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JDBCHandlerTest {
    @MockBean
    JDBCWriter jdbcWriter;
    @MockBean
    JDBCReader jdbcReader;
    @MockBean
    JDBCHandler jdbcHandler;
    private String url = "jdbc:mysql://localhost:3306/mydb?serverTimezone=UTC";
    private String user = "DB";
    private String pass = "333";

    @Test
    void setConnection() {
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
            }
            // assertEquals(0,resultSet.getObject("id"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
