package com.example.user.schachapp;

public class Pawn extends Piece {
    private boolean isWhite;
    public Pawn(boolean white) {
        this.isWhite = white;
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public int getValue() {
        return 1;
    }

    @Override
    public boolean isWhite() {
        return isWhite;
    }
}
