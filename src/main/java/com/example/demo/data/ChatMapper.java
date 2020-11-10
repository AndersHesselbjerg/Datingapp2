package com.example.demo.data;

import com.example.demo.domain.Chat;
import com.example.demo.domain.ChatList;
import com.example.demo.domain.MessageList;
import com.example.demo.domain.User;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@Scope("Singleton")
public class ChatMapper {
    private final Connection connection;
    private final ChatFactory chatFactory;
    private final MessageFactory messageFactory;

    public ChatMapper(Connector connector, ChatFactory chatFactory, MessageFactory messageFactory) {
        this.connection = connector.getConnection();
        this.chatFactory = chatFactory;
        this.messageFactory = messageFactory;
    }

    public ChatList getChats(User user) {
        try {
            String statement = "SELECT * FROM mydb.chats WHERE user_id=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setInt(1, user.getUserid());
            ResultSet resultSet = preparedStatement.executeQuery();
            ChatList chats = chatFactory.batch(resultSet);
            return chats;
        } catch (SQLException sqlException) {
            throw new NullPointerException("Your SQL statement is false");
        }
    }

    public MessageList getMessages(Chat chat) {
        try {
            String statement = "SELECT text, sender_user_id FROM mydb.messages WHERE chat_id=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setInt(1, chat.getChat_id());
            ResultSet resultSet = preparedStatement.executeQuery();
            MessageList messages = messageFactory.batch(resultSet);
            return messages;
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
