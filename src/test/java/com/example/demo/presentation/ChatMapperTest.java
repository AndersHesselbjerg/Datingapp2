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
        ChatMapper chatMapper = new ChatMapper(connector, chatFactory, messageFactory, userFactory);
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
        ChatMapper chatMapper = new ChatMapper(connector, chatFactory, messageFactory, userFactory);
        ChatController chatController = new ChatController(chatMapper);

        // TEST
        User user = userMapper.getUser("Akila");
        ChatList chats = chatController.getChats(user);
        Chat chat = chats.get(0);
        String send_text = "Jeg har købt rismælk!!!";
        // chatController.sendMessage(chat, user, send_text);
        ChatList new_chats = chatController.getChats(user);
        Chat new_chat = new_chats.get(0);
        MessageList messages = new_chat.getMessages();
        assertEquals(send_text, messages.get(messages.size()-1).getText());
    }
}