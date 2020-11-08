package com.example.demo.domain.user;

import com.example.demo.data.Connector;
import com.example.demo.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class UserControllerTest {
    UserController userController;
    UserFactory userFactory;
    UserList users;

    @Test
    void buildUsers() {
        // SETUP
        Connector connector = new Connector();
        userFactory = new UserFactory();
        users = new UserList();
        userController = new UserController(users, userFactory);
        String statement = "SELECT * FROM mydb.users;";
        Connection connection = connector.setConnection();
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        // TEST STARTS HERE -------------------------------------------------------------------------
        userController.buildUsers(resultSet);
        UserList new_users = userController.fetchUsers();

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
        UserList even_newer_users = userController.fetchUsers();
        assertEquals(0, even_newer_users.size());

    }
}