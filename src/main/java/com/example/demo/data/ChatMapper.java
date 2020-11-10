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

    public ChatList getChats(int userId) {
        try {
            String statement = "SELECT * FROM mydb.chats WHERE user_id=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            ChatList chats = chatFactory.batch(resultSet);
            return chats;
        } catch (SQLException sqlException) {
            throw new NullPointerException("Your SQL statement is false");
        }
    }

    public MessageList getMessages(int chatId) {
        try {
            String statement = "SELECT text, sender_user_id FROM mydb.messages WHERE chat_id=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setInt(1, chatId);
            ResultSet resultSet = preparedStatement.executeQuery();
            MessageList messages = messageFactory.batch(resultSet);
            return messages;
        } catch (SQLException sqlException) {
            throw new NullPointerException("Your SQL statement is false");
        }
    }

    public void sendMessage(int chatId, String text, int userId) {
        try {
            String statement = "INSERT INTO mydb.messages (chat_id, text, sender_user_id) VALUES(?,?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setInt(1, chatId);
            preparedStatement.setString(2, text);
            preparedStatement.setInt(3, userId);
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            throw new NullPointerException("Your SQL statement is false");
        }
    }
}
