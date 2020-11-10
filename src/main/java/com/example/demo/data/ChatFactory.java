package com.example.demo.data;

import com.example.demo.domain.Chat;
import com.example.demo.domain.ChatList;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@Scope("Singleton")
public class ChatFactory {

    public ChatList batch(ResultSet resultSet) {
        try {
            ChatList chats = new ChatList();
            while (resultSet.next()) {
                Chat chat = create(resultSet);
                chats.add(chat);
            }
            return chats;
        } catch (SQLException e) {
            throw new NullPointerException("Something is wrong with the ResultSet");
        }
    }

    public Chat create(ResultSet resultSet) {
        try {
                if (resultSet.isBeforeFirst()) {
                    resultSet.next();
                }
                int chat_id = resultSet.getInt("chat_id");
                return new Chat(chat_id);
        } catch (SQLException sqlException) {
            throw new NullPointerException("Something went wrong, check your connection to dmbs");
        }
    }
}
