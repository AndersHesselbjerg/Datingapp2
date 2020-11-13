package com.example.demo.domain;

public class Candidate {
    private int user_id;
    private int owner_id;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }

    public Candidate (int user, int owner_id) {
        this.user_id = user;
        this.owner_id = owner_id;
    }



}
