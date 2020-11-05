package com.example.demo.domain.User;

import com.example.demo.domain.JDBC.JDBCReader;
import com.example.demo.models.User;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class UserHandlerTest {
    String url;
    String user;
    String pass;
    UserHandler userHandler;
    UserFactory userFactory;
    UserList users;

    UserHandlerTest() {
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
    void buildUsers() {
        try {
            // SETUP
            JDBCReader jdbcReader = new JDBCReader();
            userHandler = new UserHandler();
            userFactory = new UserFactory();
            users = new UserList();
            userHandler.setUserFactory(userFactory);
            userHandler.setUserList(users);
            int[] ids = {0, 1};
            Connection connection = DriverManager.getConnection(url, user, pass);
            jdbcReader.setConnection(connection);
            ResultSet resultSet = jdbcReader.getUsers(ids);

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

        } catch (SQLException sqlException) {
            throw new NullPointerException();
        }
    }
}