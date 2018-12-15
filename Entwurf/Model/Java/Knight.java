package com.example.user.schachapp;

public class Knight extends Piece {
    private boolean isWhite;
    public Knight(boolean white) {
        this.isWhite = white;
    }

    @Override
    public String toString() {
        return "S";
    }

    @Override
    public int getValue() {
        return 3;
    }

    @Override
    public boolean isWhite() {
        return isWhite;
    }
}
