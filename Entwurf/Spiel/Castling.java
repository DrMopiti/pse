package com.example.user.schachapp;

public class Castling extends Move {
    private Move rookMove;
    public Castling(King king, Position start, Position goal) {
        super(king, start, goal);
    }
    public Move getRookMove() {return rookMove;}
}
