package com.example.user.schachapp.chessLogic;

public class Player {
    private User user;
    public Player(User user) {
        this.user = user;
    }
    public String getName() {
        return user.getName();
    }
}
