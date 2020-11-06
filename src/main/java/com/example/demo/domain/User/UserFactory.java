package com.example.demo.domain.User;

import com.example.demo.models.User;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@Scope("singleton")
public class UserFactory {
    public User create(ResultSet resultSet) {
        try {
            String id = resultSet.getString("ID");
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            String firstname = resultSet.getString("first_name");
            String lastname = resultSet.getString("last_name");
            String description = resultSet.getString("description");
            String tags = resultSet.getString("tags");
            return new User(id, username, password, firstname, lastname, description, tags);
        } catch (SQLException sqlException) {
            throw new NullPointerException("Something went wrong, check your connection to dmbs");
        }
    }
}
