package com.example.demo.domain.user;

import com.example.demo.data.jdbc.JDBCConnector;
import com.example.demo.models.User;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserFactoryTest {

    @Test
    void create() {
        try {
            // SETUP
            JDBCConnector jdbcConnector = new JDBCConnector();
            String statement = "SELECT * FROM mydb.users;";
            jdbcConnector.setConnection();
            ResultSet resultSet = jdbcConnector.query(statement);

            // TEST
            UserFactory userFactory = new UserFactory();
            if (resultSet.next()) {
                User rasmus = userFactory.create(resultSet);

                assertEquals("1", rasmus.getUserid());
                assertEquals("Akila", rasmus.getUserName());
                assertEquals("Hello World", rasmus.getPassword());
                assertEquals("Rasmus", rasmus.getFirstName());
                assertEquals("Bilgård", rasmus.getLastName());
                assertNull(rasmus.getDescription());
                assertNull(rasmus.getTags());
            }
        } catch (SQLException e) {
            throw new NullPointerException("Something went wrong, check your connection to dbms");
        }
    }
}