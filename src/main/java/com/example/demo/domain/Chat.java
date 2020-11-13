package com.example.demo.domain;

public class Chat {
    private final int chat_id;
    private UserList users;
    private MessageList messages;

    public Chat(int chat_id) {
        this.chat_id = chat_id;
    }

    public void setMessages(MessageList messages) {
        this.messages = messages;
    }

    public void setUsers(UserList users) {
        this.users = users;
    }

    public MessageList getMessages() {
        return messages;
    }

    public UserList getUsers() {
        return users;
    }

    public String getNames() {
        String usernames ="";
        for (User user: users) {
            usernames = usernames + " " +user.getUserName();
        } return usernames;
    }

    public int getChat_id() {
        return chat_id;
    }
}
