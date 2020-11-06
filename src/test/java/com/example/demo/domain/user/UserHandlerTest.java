package com.example.demo.domain.user;

import com.example.demo.data.jdbc.JDBCConnector;
import com.example.demo.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class UserHandlerTest {
    UserHandler userHandler;
    UserFactory userFactory;
    UserList users;

    @Test
    void buildUsers() {
        // SETUP
        JDBCConnector jdbcConnector = new JDBCConnector();
        userFactory = new UserFactory();
        users = new UserList();
        userHandler = new UserHandler(users, userFactory);
        String statement = "SELECT * FROM mydb.users;";
        jdbcConnector.setConnection();
        ResultSet resultSet = jdbcConnector.query(statement);

        // TEST STARTS HERE -------------------------------------------------------------------------
        userHandler.buildUsers(resultSet);
        UserList new_users = userHandler.fetchUsers();

        // TESTING IF USER INFORMATION FITS THE DATABASE INFORMATION
        User rasmus = new_users.get(0);
        assertEquals("1", rasmus.getUserid());
        assertEquals("Akila", rasmus.getUserName());
        assertEquals("Hello World", rasmus.getPassword());
        assertEquals("Rasmus", rasmus.getFirstName());
        assertEquals("Bilgård", rasmus.getLastName());
        assertNull(rasmus.getDescription());
        assertNull(rasmus.getTags());

        // TESTING IF THE USERLIST IS ERASED AFTER BEING FETCHED FOR THE FIRST TIME
        UserList even_newer_users = userHandler.fetchUsers();
        assertEquals(0, even_newer_users.size());

    }
}