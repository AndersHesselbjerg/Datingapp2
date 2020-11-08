package com.example.demo.data;

import com.example.demo.domain.User;
import com.example.demo.domain.UserList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    @Test
    void getAllUsers() {
        // SETUP
        Connector connector = new Connector();
        UserFactory userFactory = new UserFactory();
        UserMapper userMapper = new UserMapper(connector, userFactory);

        // TEST
        UserList users = userMapper.getAllUsers();
        for (int i = 0; i < users.size(); i++) {
            int userId = users.get(i).getUserid();
            assertEquals(i+1, userId);
        }
    }

    @Test
    void getUsers() {
        // SETUP
        Connector connector = new Connector();
        UserFactory userFactory = new UserFactory();
        UserMapper userMapper = new UserMapper(connector, userFactory);

        // TEST
        UserList users = userMapper.getUsers(0,3);
        for (int i = 0; i < users.size(); i++) {
            int expectedId = i+1;
            int userId = users.get(i).getUserid();
            assertEquals(expectedId, userId);
        }
        assertEquals(3, users.size());
    }

    @Test
    void getUser() {
        // SETUP
        Connector connector = new Connector();
        UserFactory userFactory = new UserFactory();
        UserMapper userMapper = new UserMapper(connector, userFactory);

        // TEST
        User akila = userMapper.getUser("Akila");
        User dragonSlayer = userMapper.getUser("DragonSlayer");
        String rasmus = akila.getFirstName();
        String thomas = dragonSlayer.getFirstName();
        assertEquals("Rasmus", akila.getFirstName());
        assertEquals("Kim", dragonSlayer.getFirstName());
    }

    @Test
    void updateUser() {
        // SETUP
        Connector connector = new Connector();
        UserFactory userFactory = new UserFactory();
        UserMapper userMapper = new UserMapper(connector, userFactory);

        // TEST
        String new_password = "";
        User lilu = userMapper.getUser("Lilu");
        if (lilu.getPassword().equals("Hello World")) {
            new_password = "World Hello";
            lilu.setPassword(new_password);
        } else {
            new_password = "Hello World";
            lilu.setPassword(new_password);
        }
        userMapper.updateUser(lilu);
        User user = userMapper.getUser("Lilu");
        assertEquals(new_password, user.getPassword());
    }
}