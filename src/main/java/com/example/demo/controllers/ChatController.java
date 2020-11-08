package com.example.demo.controllers;

import com.example.demo.data.ChatMapper;

public class ChatController {
    private final ChatMapper chatMapper;

    ChatController(ChatMapper chatMapper) {
        this.chatMapper = chatMapper;
    }
}
