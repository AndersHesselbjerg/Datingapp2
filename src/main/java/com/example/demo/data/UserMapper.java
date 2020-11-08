package com.example.demo.data;

import com.example.demo.data.Connector;
import com.example.demo.domain.UserList;
import com.example.demo.domain.User;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


@Component
@Scope("Singleton")
public class UserMapper {
    private final Connection connection;
    private final UserFactory userFactory;

    UserMapper(Connector connector, UserFactory userFactory) {
        this.connection = connector.setConnection();
        this.userFactory = userFactory;
    }

    public UserList getAllUsers() {
        String statement = "SELECT * FROM mydb.users;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            ResultSet resultSet = preparedStatement.executeQuery();
            UserList users = userFactory.batch(resultSet);
            return users;
        } catch (SQLException sqlException) {
            throw new NullPointerException("Your SQL statement is false");
        }
    }

    public UserList getUsers(int limit, int start_row) {
        String statement = "SELECT * FROM mydb.users LIMIT ?,?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setInt(1, limit);
            preparedStatement.setInt(2, start_row);
            ResultSet resultSet = preparedStatement.executeQuery();
            UserList users = userFactory.batch(resultSet);
            return users;
        } catch (SQLException sqlException) {
            throw new NullPointerException("Your SQL statement is false");
        }
    }

    public User getUser(String username) {
        String statement = "SELECT * FROM mydb.users WHERE username LIKE ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            User user = userFactory.create(resultSet);
            return user;
        } catch (SQLException sqlException) {
            throw new NullPointerException("Your SQL statement is false");
        }
    }

    public void updateUser(User user) {
        String statement = "UPDATE users SET username=?, password=?, first_name=?, last_name=?, description=?, tags=? WHERE ID = 4;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4, user.getLastName());
            preparedStatement.setString(5, user.getDescription());
            preparedStatement.setString(6, user.getTags());
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            throw new NullPointerException("Your SQL statement is false");
        }
    }
}
