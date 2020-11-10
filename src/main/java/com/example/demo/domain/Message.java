package com.example.demo.domain;

public class Message {
    private final String text;
    private final int senderId;

    public Message(String text, int senderId) {
        this.text = text;
        this.senderId = senderId;
    }

    public String getText() {
        return text;
    }

    public int getSenderId() {
        return senderId;
    }
}
