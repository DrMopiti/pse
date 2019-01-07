package com.example.user.schachapp;

import java.util.List;

public class King extends Piece  {
    private static final String KING_CHAR = "K";
    private static final int KING_VALUE = 0;
    public King(boolean isWhite) {
        super(isWhite, KING_VALUE, KING_CHAR);
    }
    @Override
    public List<Move> getMovement(Position position, BoardState board) {
        return null;
    }
}
