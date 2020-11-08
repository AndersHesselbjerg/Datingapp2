package com.example.demo.data;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("Singleton")
public class ChatMapper {
    private final Connector connector;
    private final ChatFactory chatFactory;

    ChatMapper(Connector connector, ChatFactory chatFactory) {
        this.connector = connector;
        this.chatFactory = chatFactory;
    }
}
