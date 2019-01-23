package com.example.user.schachapp.chessLogic;

import com.example.user.schachapp.chessLogic.Piece;

public class Tile {
    private Piece piece;

    public Tile() {
        this.piece = null;
    }

    public Tile(Piece piece) {
        this.piece = piece;
    }
    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }
    public void removePiece() {
        this.piece = null;
    }
}
