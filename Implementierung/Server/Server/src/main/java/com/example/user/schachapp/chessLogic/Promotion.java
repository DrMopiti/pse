package com.example.user.schachapp.chessLogic;

public class Promotion extends Move {
    private Piece promotion;
    public Promotion(Position start, Position goal, Piece promotion) {
        super(start, goal);
        if ((promotion.isWhite() && !(start.getY() == 6 && goal.getY() == 7)) || (!promotion.isWhite() && !(start.getY() == 1 && goal.getY() == 0))) {
            throw new IllegalArgumentException("angaben für start und goal müssen richtig definiert sein");
        }

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
