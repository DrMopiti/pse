package com.example.user.schachapp;

public abstract class BoardState {

    public abstract void applyMove(Move move);
    public abstract String toString();
    public abstract Piece getPieceAt(Position p);
    public abstract void setPieceAt(Position p, Piece piece);
}
