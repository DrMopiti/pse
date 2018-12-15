package com.example.user.schachapp;

public class Promotion extends Move {
    private Piece promotion;
    public Promotion(Pawn pawn, Position start, Position goal, Piece promotion) {
        super(pawn, start, goal);
        this.promotion = promotion;
    }

    public Piece getPromotion() {
        return promotion;
    }
}
