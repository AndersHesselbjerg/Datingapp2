package com.example.demo.domain.User;

import com.example.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Component
@Scope("Singleton")
public class UserHandler {
    private UserList userList;
    private UserFactory userFactory;

    @Autowired
    public void setUserList(UserList userList) {
        this.userList = userList;
    }

    @Autowired
    public void setUserFactory(UserFactory userFactory) {
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
