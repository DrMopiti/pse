package com.example.user.schachapp;

public class EnPassant extends Move {
    Position removePawn;

    public EnPassant(Pawn pawn, Position start, Position goal) {
        super(pawn, start, goal);
    }

    public Position getRemovePawn() {
        return removePawn;
    }
}