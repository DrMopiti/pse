package com.example.user.schachapp;

public class Tile {
    private Piece piece;

    public Tile() {
        this.piece = null;
    }

    public Tile(Piece piece) {

    }
    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }
}
