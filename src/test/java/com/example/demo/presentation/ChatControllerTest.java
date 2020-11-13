package com.example.demo.presentation;

import com.example.demo.data.*;
import com.example.demo.domain.Chat;
import com.example.demo.domain.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChatControllerTest {

    @Test
    void createChat() {
        // SETUP
        Connector connector = new Connector();
        ChatFactory chatFactory = new ChatFactory();
        UserFactory userFactory = new UserFactory();
        UserMapper userMapper = new UserMapper(connector, userFactory);
        MessageFactory messageFactory = new MessageFactory(userMapper);
        ChatMapper chatMapper = new ChatMapper(connector, chatFactory, messageFactory, userFactory);
        ChatController chatController = new ChatController(chatMapper);

        // TEST
        User user_01 = userMapper.getUser("Akila");
        User user_02 = userMapper.getUser("DragonSlayer");
        // Chat new_chat = chatController.createChat(user_01.getUserid(), user_02.getUserid());
        Chat new_chat = chatController.getChatById(7);
        assertEquals("Akila", new_chat.getUsers().get(0).getUserName());
        assertEquals("DragonSlayer", new_chat.getUsers().get(1).getUserName());
    }
}