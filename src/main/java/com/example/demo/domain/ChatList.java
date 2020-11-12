package com.example.demo.domain;

import java.util.ArrayList;

public class ChatList extends ArrayList<Chat> {

    public Chat getById(int id) {
        for (int i = 0; i < size(); i++) {
            if (get(i).getChat_id() == id) {
                return get(i);
            }
        }
        return null;
    }

}
