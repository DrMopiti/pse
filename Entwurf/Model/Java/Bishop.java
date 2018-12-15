package com.example.user.schachapp;

public class Bishop extends Piece {
    private boolean isWhite;
    public Bishop(boolean white) {
        this.isWhite = white;
    }

    @Override
    public String toString() {
        return "L";
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
