package com.example.user.schachapp.chessLogic;

/**
 * A promotion which extends move and consists of start, goal and the piece to be promoted to
 */
public class Promotion extends Move {
    private Piece promotion;
    public Promotion(Position start, Position goal, Piece promotion) {
        super(start, goal);
        if ((promotion.isWhite() && !(start.getY() == 6 && goal.getY() == 7)) || (!promotion.isWhite() && !(start.getY() == 1 && goal.getY() == 0))) {
            throw new IllegalArgumentException("Angaben für start und goal müssen richtig definiert sein");
        }
        if (promotion instanceof King || promotion instanceof Pawn) {
            throw new IllegalArgumentException("Kann nicht in König oder Bauern umwandeln");
        }
        this.promotion = promotion;
    }

    /**
     *
     * @return the promotion piece
     */
    public Piece getPromotion() {
        return promotion;
    }

    /**
     * Returns the string representation of this move, which has an additional char at the end, indicating the promotion piece
     * @return string representation of this move
     */
    @Override
    public String toString() {
        return (start.toString() + "-" + goal.toString() + "-" + promotion.toString());
    }
}
