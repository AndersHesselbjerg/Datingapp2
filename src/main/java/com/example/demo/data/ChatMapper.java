package com.example.demo.data;

import com.example.demo.domain.*;
import com.mysql.cj.protocol.Resultset;
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
    private final UserFactory userFactory;

    public ChatMapper(Connector connector, ChatFactory chatFactory, MessageFactory messageFactory, UserFactory userFactory) {
        this.connection = connector.getConnection();
        this.chatFactory = chatFactory;
        this.messageFactory = messageFactory;
        this.userFactory = userFactory;
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

    public Chat getChatById(int id) {
        String statement = "SELECT * FROM mydb.chats WHERE ID LIKE ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Chat chat = chatFactory.create(resultSet);
            return chat;
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

    public UserList getUsers(int chatId) {
        try {
            String statement = "SELECT user_id FROM mydb.chats WHERE chat_id=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setInt(1, chatId);
            ResultSet resultSet = preparedStatement.executeQuery();
            UserList users = new UserList();
            String new_statement = "SELECT * FROM mydb.users WHERE ID=?;";
            while (resultSet.next()) {
                PreparedStatement preparedStatement1 = connection.prepareStatement(new_statement);
                preparedStatement1.setInt(1, resultSet.getInt("user_id"));
                ResultSet resultSet1 = preparedStatement1.executeQuery();
                users.add(userFactory.create(resultSet1));
            }
            return users;
        } catch (SQLException sqlException) {
            throw new NullPointerException(sqlException.getMessage());
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
