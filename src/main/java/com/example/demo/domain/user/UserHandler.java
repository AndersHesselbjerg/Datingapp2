package com.example.demo.domain.user;

import com.example.demo.models.User;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@Scope("Singleton")
public class UserHandler {
    private UserList userList;
    private UserFactory userFactory;

    UserHandler(UserList userList, UserFactory userFactory) {
        this.userList = userList;
        this.userFactory = userFactory;
    }

    public void buildUsers(ResultSet resultSet) {
        try {
            while (resultSet.next()) {
                User user = userFactory.create(resultSet);
                userList.add(user);
            }
        } catch (SQLException e) {
            throw new NullPointerException("Something is wrong with the ResultSet");
        }
    }

    public UserList fetchUsers() {
        UserList users = (UserList) userList.clone();
        userList = new UserList();
        return users;
    }
}
