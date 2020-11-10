package com.example.demo.data;

import com.example.demo.domain.Message;
import com.example.demo.domain.MessageList;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@Scope("Singleton")
public class MessageFactory {

    public MessageList batch(ResultSet resultSet) {
        try {
            MessageList messages = new MessageList();
            while (resultSet.next()) {
                Message message = create(resultSet);
                messages.add(message);
            }
            return messages;
        } catch (SQLException e) {
            throw new NullPointerException("Something is wrong with the ResultSet");
        }
    }

    public Message create(ResultSet resultSet) {
        try {
            if (resultSet.isBeforeFirst()) {
                resultSet.next();
            }
            String text = resultSet.getString("text");
            int sender_user_id = resultSet.getInt("sender_user_id");
            return new Message(text, sender_user_id);
        } catch (SQLException sqlException) {
            throw new NullPointerException("Something went wrong, check your connection to dmbs");
        }
    }
}
