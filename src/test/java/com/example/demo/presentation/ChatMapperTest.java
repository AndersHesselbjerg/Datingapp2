package com.example.demo.presentation;

import com.example.demo.data.*;
import com.example.demo.domain.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
    void sendMessage() {
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
        String send_text = "Tester";
        // chatController.sendMessage(chat, user, send_text);
        ChatList new_chats = chatController.getChats(user);
        Chat new_chat = chats.get(0);
        MessageList messages = new_chat.getMessages();
        assertEquals("Tester", messages.get(messages.size()-1).getText());
    }
}