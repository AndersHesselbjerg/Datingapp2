package com.example.demo.data;

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

    public UserMapper(Connector connector, UserFactory userFactory) {
        this.connection = connector.getConnection();
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

    public UserList getUsers(int start_row, int limit) {
        String statement = "SELECT * FROM mydb.users LIMIT ?,?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setInt(1, start_row);
            preparedStatement.setInt(2, limit);
            ResultSet resultSet = preparedStatement.executeQuery();
            UserList users = userFactory.batch(resultSet);
            return users;
        } catch (SQLException sqlException) {
            throw new NullPointerException("Your SQL statement is false");
        }
    }

    public User getUserById(int id) {
        String statement = "SELECT * FROM mydb.users WHERE ID LIKE ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            User user = userFactory.create(resultSet);
            return user;
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
        String statement = "UPDATE users SET " +
                "username=?, password=?, first_name=?, last_name=?, credit_info=?, phone_number=?, email=?, description=?, tags=?, user_score=? WHERE ID=?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setInt(11, user.getUserid());
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4, user.getLastName());
            preparedStatement.setString(5, user.getCreditInfo());
            preparedStatement.setString(6, user.getPhone());
            preparedStatement.setString(7, user.getMail());
            preparedStatement.setString(8, user.getDescription());
            preparedStatement.setString(9, user.getTags());
            preparedStatement.setInt(10, user.getScore());
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            throw new NullPointerException("Your SQL statement is false");
        }
    }

    public void insertUser(User user) {
        String statement = "INSERT INTO users " +
            "(username, password, first_name, last_name, credit_info, phone_number, email, description, tags, user_score)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4, user.getLastName());
            preparedStatement.setString(5, user.getCreditInfo());
            preparedStatement.setString(6, user.getPhone());
            preparedStatement.setString(7, user.getMail());
            preparedStatement.setString(8, user.getDescription());
            preparedStatement.setString(9, user.getTags());
            preparedStatement.setInt(10, user.getScore());
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            throw new NullPointerException("Your SQL statement is false");
        }
    }

    public void deleteUser(User user) {
        String statement = "DELETE FROM users WHERE ID=?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setInt(1, user.getUserid());
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            throw new NullPointerException("Your SQL statement is false");
        }
    }
}
