package com.example.demo.data;

public class ChatMapper {
    private final Connector connector;
    private final ChatFactory chatFactory;

    ChatMapper(Connector connector, ChatFactory chatFactory) {
        this.connector = connector;
        this.chatFactory = chatFactory;
    }
}
