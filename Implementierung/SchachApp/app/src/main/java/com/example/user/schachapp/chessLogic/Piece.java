package com.example.user.schachapp.chessLogic;

import android.widget.ImageView;

import java.util.List;

/**
 *  the abstract concept of a chesspiece
 */
public abstract class Piece {
    protected boolean isWhite;
    protected int value;
    protected String representation;
    protected Piece(boolean isWhite, int value, String representation) {
        this.isWhite = isWhite;
        this.value = value;
        this.representation = representation;
    }

    /**
     * Returns a string to represent this piece, considering its color
     * @return one-char-string
     */
    public String toString() {
        if (isWhite) {
            return representation;
        } else {
            return representation.toLowerCase();
        }
    }

    /**
     *
     * @return the pieces value
     */
    public int getValue() {
        return value;
    }

    /**
     *
     * @return the pieces color
     */
    public boolean isWhite() {
        return isWhite;
    }

    /**
     *
     * @return filename for the image of this piece
     */
    public abstract String getImageName();

    /**
     * Calculates the movement of a piece on a given board for a given position and returns it as a list of moves.
     * The method will not check if there is the correct on the given position, it will also ignore if the piece is pinned.
     * @param position the position on which the piece stands
     * @param board the board on which the movement should be calculated
     * @return a list of moves
     */
    public abstract List<Move> getMovement(Position position, BoardState board);
}
