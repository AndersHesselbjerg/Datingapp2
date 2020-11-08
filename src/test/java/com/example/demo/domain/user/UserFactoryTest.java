package com.example.demo.domain.user;

import com.example.demo.data.Connector;
import com.example.demo.models.User;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserFactoryTest {

    @Test
    void create() {
        try {
            // SETUP
            Connector connector = new Connector();
            String statement = "SELECT * FROM mydb.users;";
            Connection connection = connector.setConnection();
            ResultSet resultSet = null;
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(statement);
                resultSet = preparedStatement.executeQuery();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }

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