package com.example.demo.presentation;

import com.example.demo.data.ChatMapper;
import com.example.demo.domain.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("Singleton")
public class ChatController {
    private final ChatMapper chatMapper;

    ChatController(ChatMapper chatMapper) {
        this.chatMapper = chatMapper;
    }

    public ChatList getChats(User user) {
        return chatMapper.getChats(user);
    }

    public MessageList getMessages(Chat chat) {
        return chatMapper.getMessages(chat);
    }
}
