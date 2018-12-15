package com.example.user.schachapp;

public class Queen extends Piece {
    private boolean isWhite;
    public Queen(boolean white) {
        this.isWhite = white;
    }

    @Override
    public String toString() {
        return "D";
    }

    @Override
    public int getValue() {
        return 9;
    }

    @Override
    public boolean isWhite() {
        return isWhite;
    }
}
