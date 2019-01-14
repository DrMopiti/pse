package com.example.user.schachapp;

import java.util.List;

public abstract class Piece {
    protected boolean isWhite;
    protected int value;
    protected String representation;
    protected Piece(boolean isWhite, int value, String representation) {
        this.isWhite = isWhite;
        this.value = value;
        this.representation = representation;
    }
    public String toString() {
        return this.representation;
    };
    public int getValue() {
        return this.value;
    }
    public boolean isWhite() {
        return this.isWhite;
    };
    public abstract List<Move> getMovement(Position position, BoardState board) throws IllegalPositionException;
}
