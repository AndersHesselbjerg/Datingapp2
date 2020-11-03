package com.example.demo.services.JDBC;

import com.example.demo.models.User;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;

@Component
@Scope("Singleton")
public class JDBCWriter {
    private Connection connection;
    private PreparedStatement preparedStatement;

    public void setConnection(Connection connection) { this.connection = connection; }

    public void addUser(User user) {}

    public void editUser(User user) {}

    public void removeUser(int user_id) {}

    public void writeMessage(int chat_id, int sender_id) {}

    public void editMessage(int chat_id, int message_id) {}

    public void deleteMessage(int chat_id, int message_id) {}

    public void addToPair(int pair_id, int user_id) {}

    public void removeFromPair(int pair_id, int user_id) {}
}
