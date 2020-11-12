package com.example.demo.domain;

public class Candidate {
    private int userID;
    private int pairID;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getPairID() {
        return pairID;
    }

    public void setPairID(int pairID) {
        this.pairID = pairID;
    }

    public Candidate (int user, int pairID) {
        this.userID = user;
        this.pairID = pairID;
    }



}
