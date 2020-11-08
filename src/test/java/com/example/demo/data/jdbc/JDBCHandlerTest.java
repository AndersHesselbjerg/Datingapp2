package com.example.demo.data.jdbc;

import com.example.demo.data.Connector;
import com.example.demo.models.User;
import com.mysql.cj.jdbc.result.ResultSetImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JDBCHandlerTest {

    @Test
    void setConnection() {
        Connector connector = new Connector();
        Connection test = connector.setConnection();
        assertNotNull(test);
    }

    @Test
    void getUsers() {
        Connector connector = new Connector();
        try {
            Connection connection = connector.setConnection();
            String statement = "SELECT * FROM mydb.users;";
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            ResultSet resultSet = preparedStatement.executeQuery();
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

    @Test
    void editUser() {
        Connector connector = new Connector();
        JDBCHandler jdbcHandler = new JDBCHandler(connector);

        User user = new User("4", "Lilu", "Hello World", "Sabine", "Vedel", null, null);
        jdbcHandler.updateUser(user);
    }
}
