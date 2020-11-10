package com.example.demo.domain;

public class Chat {
    private final int chat_id;
    private MessageList messages;

    public Chat(int chat_id) {
        this.chat_id = chat_id;
    }

    public void setMessages(MessageList messages) {
        this.messages = messages;
    }

    public MessageList getMessages() {
        return messages;
    }

    public int getChat_id() {
        return chat_id;
    }
}
