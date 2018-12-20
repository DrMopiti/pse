package com.example.user.schachapp;

import java.util.List;

public class Bishop extends Piece {
    private static final String BISHOP_CHAR = "L";
    private static final int BISHOP_VALUE = 3;
    public Bishop(boolean isWhite) {
        super(isWhite, BISHOP_VALUE, BISHOP_CHAR);
    }
    @Override
    public List<Move> getMovement(Position position, BoardState board) {
        return null;
    }
}
