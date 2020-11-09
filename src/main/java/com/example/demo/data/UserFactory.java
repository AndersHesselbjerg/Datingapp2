package com.example.demo.data;

import com.example.demo.domain.UserList;
import com.example.demo.domain.User;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@Scope("singleton")
public class UserFactory {

    public UserList batch(ResultSet resultSet) {
        try {
            UserList users = new UserList();
            while (resultSet.next()) {
                User user = create(resultSet);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new NullPointerException("Something is wrong with the ResultSet");
        }
    }

    public User create(ResultSet resultSet) {
        try {
            if (resultSet.isBeforeFirst()) {
                resultSet.next();
            }
            int id = resultSet.getInt("ID");
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            String firstname = resultSet.getString("first_name");
            String lastname = resultSet.getString("last_name");
            String creditInfo = resultSet.getString("credit_info");
            String phone = resultSet.getString("phone_number");
            String mail = resultSet.getString("email");
            String description = resultSet.getString("description");
            String tags = resultSet.getString("tags");
            String time_of_registry = resultSet.getString("time_of_registry");
            int score = resultSet.getInt("user_score");
            return new User(id, username, password, firstname, lastname, creditInfo, phone, mail, description, tags, time_of_registry, score);
        } catch (SQLException sqlException) {
            throw new NullPointerException("Something went wrong, check your connection to dmbs");
        }
    }
}
