package com.example.user.schachapp.chessLogic;

public class Promotion extends Move {
    private Piece promotion;
    public Promotion(Position start, Position goal, Piece promotion) {
        super(start, goal);
        this.promotion = promotion;
    }

    public Piece getPromotion() {
        return promotion;
    }

    @Override
    public String toString() {
        return (start.toString() + "-" + goal.toString() + "-" + promotion.toString());
    }
}
