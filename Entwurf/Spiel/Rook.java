package com.example.user.schachapp;

public class Rook extends Piece {
    private boolean isWhite;
    public Rook(boolean white) {
        this.isWhite = white;
    }

    @Override
    public String toString() {
        return "T";
    }

    @Override
    public int getValue() {
        return 5;
    }

    @Override
    public boolean isWhite() {
        return isWhite;
    }
}
