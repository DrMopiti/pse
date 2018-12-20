package com.example.user.schachapp;

import java.util.List;

public class Knight extends Piece {
    private static final String KNIGHT_CHAR = "S";
    private static final int KNIGHT_VALUE = 3;
    public Knight(boolean isWhite) {
        super(isWhite, KNIGHT_VALUE, KNIGHT_CHAR);
    }
    @Override
    public List<Move> getMovement(Position position, BoardState board) {
        return null;
    }
}
