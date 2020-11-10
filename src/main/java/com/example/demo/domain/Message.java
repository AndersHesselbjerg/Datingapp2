package com.example.demo.domain;

public class Message {
    private final String text;
    private final String sender;

    public Message(String text, String sender) {
        this.text = text;
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public String getSender() {
        return sender;
    }
}
