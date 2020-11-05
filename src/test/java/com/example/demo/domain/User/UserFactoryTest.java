package com.example.demo.domain.User;

import com.example.demo.domain.JDBC.JDBCReader;
import com.example.demo.models.User;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class UserFactoryTest {

    private String url;
    private String user;
    private String pass;

    UserFactoryTest() {
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
    void create() {
        try {
            // SETUP
            JDBCReader jdbcReader = new JDBCReader();
            int[] ids = {0, 1};
            Connection connection = DriverManager.getConnection(url, user, pass);
            jdbcReader.setConnection(connection);
            ResultSet resultSet = jdbcReader.getUsers(ids);

            // TEST
            UserFactory userFactory = new UserFactory();
            User rasmus = userFactory.create(resultSet);

            assertEquals("1", rasmus.getUserid());
            assertEquals("Akila", rasmus.getUserName());
            assertEquals("Hello World", rasmus.getPassword());
            assertEquals("Rasmus", rasmus.getFirstName());
            assertEquals("Bilgård", rasmus.getLastName());
            assertNull(rasmus.getDescription());
            assertNull(rasmus.getTags());

        } catch (SQLException e) {
            throw new NullPointerException("Something went wrong, check your connection to dbms");
        }
    }
}