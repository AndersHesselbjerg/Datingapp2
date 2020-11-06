package com.example.demo.data.jdbc;

import com.mysql.cj.jdbc.result.ResultSetImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JDBCHandlerTest {

    @Test
    void setConnection() {
        JDBCConnector jdbcConnector = new JDBCConnector();
        boolean test = jdbcConnector.setConnection();
        assertEquals(true, test);
    }

    @Test
    void getUsers() {
        JDBCConnector jdbcConnector = new JDBCConnector();
        try {
            jdbcConnector.setConnection();
            String statement = "SELECT * FROM mydb.users;";
            ResultSet resultSet = jdbcConnector.query(statement);
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
