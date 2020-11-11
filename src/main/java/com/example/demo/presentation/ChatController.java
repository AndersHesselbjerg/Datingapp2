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
        ChatList chats = chatMapper.getChats(user.getUserid());
        for (Chat chat: chats) {
            MessageList messages = getMessages(chat);
            chat.setMessages(messages);
        }
        return chats;
    }

    private MessageList getMessages(Chat chat) {
        return chatMapper.getMessages(chat.getChat_id());
    }

    public void sendMessage(Chat chat, User user, String text) {
        chatMapper.sendMessage(chat.getChat_id(), text, user.getUserid());
    }
}
