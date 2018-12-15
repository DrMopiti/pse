package com.example.user.schachapp;

public class Tile {
    private Piece piece;

    public Tile() {
        this.piece = null;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }
}
