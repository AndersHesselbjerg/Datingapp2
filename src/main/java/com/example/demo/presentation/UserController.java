package com.example.demo.presentation;

import com.example.demo.data.UserMapper;
import com.example.demo.domain.User;
import com.example.demo.domain.UserList;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

@Component
@Scope("Singleton")
public class UserController {
    private final UserMapper userMapper;

    UserController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public UserList getAllUsers() {
        return userMapper.getAllUsers();
    }

    public UserList getUsers(int limit, int start_row) {
        return userMapper.getUsers(limit, start_row);
    }

    public User getUserById(int id) {
        return userMapper.getUserById(id);
    }

    public User getUser(String username) {
        return userMapper.getUser(username);
    }

    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    public void insertUser(User user) {
        userMapper.insertUser(user);
    }

    public void deleteUser(User user) {
        userMapper.deleteUser(user);
    }

    public void addProfilePicture(int user_id, MultipartFile file) {
        try {
            byte[] fileAsBytes = file.getBytes();
            Blob fileAsBlob = new SerialBlob(fileAsBytes);
            userMapper.uploadImg(user_id, fileAsBlob);
        } catch (IOException | SQLException ioException) {
            throw new NullPointerException(ioException.getMessage());
        }
    }
}
