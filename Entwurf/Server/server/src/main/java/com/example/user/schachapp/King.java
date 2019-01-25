package com.example.user.schachapp;

public class King extends Piece  {
    private boolean isWhite;
    public King(boolean white) {
        this.isWhite = white;
    }

    @Override
    public String toString() {
        return "K";
    }

    @Override
    public int getValue() {
        return 0;
    }

    @Override
    public boolean isWhite() {
        return isWhite;
    }
}
