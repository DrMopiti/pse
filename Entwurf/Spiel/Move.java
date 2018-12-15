package com.example.user.schachapp;

public class Move {
    private Piece piece;
    private Position start;
    private Position goal;

    public Move (String moveString) {

    }
    public Move(Piece piece, Position start, Position goal) {
        this.piece = piece;
        this.start = start;
        this.goal = goal;
    }

    public Piece getPiece() {
        return piece;
    }

    public Position getStart() {
        return start;
    }

    public Position getGoal() {
        return goal;
    }

    @Override
    public String toString() {
        return null;
    }
}
