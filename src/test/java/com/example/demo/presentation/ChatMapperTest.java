package com.example.demo.presentation;

import com.example.demo.data.*;
import com.example.demo.domain.Chat;
import com.example.demo.domain.ChatList;
import com.example.demo.domain.Message;
import com.example.demo.domain.User;
import org.junit.jupiter.api.Test;

class ChatMapperTest {

    @Test
    void getChats() {
        // SETUP
        Connector connector = new Connector();
        ChatFactory chatFactory = new ChatFactory();
        UserFactory userFactory = new UserFactory();
        UserMapper userMapper = new UserMapper(connector, userFactory);
        MessageFactory messageFactory = new MessageFactory(userMapper);
        ChatMapper chatMapper = new ChatMapper(connector, chatFactory, messageFactory);
        ChatController chatController = new ChatController(chatMapper);

        // TEST
        User user = userMapper.getUser("Akila");
        ChatList chats = chatController.getChats(user);
        Chat chat = chats.get(0);
        for (Message m: chat.getMessages()) {
            System.out.println(m.getSender() + " skrev: " + m.getText());
        }
    }

    @Test
    void getMessages() {
    }
}