package com.example.demo.presentation;

import com.example.demo.data.CommMapper;
import com.example.demo.domain.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("Singleton")
public class CommController {
    private final CommMapper commMapper;

    CommController(CommMapper commMapper) {
        this.commMapper = commMapper;
    }

    public ChatList getChats(User user) {
        return commMapper.getChats(user);
    }

    public MessageList getMessages(Chat chat) {
        return commMapper.getMessages(chat);
    }
}
