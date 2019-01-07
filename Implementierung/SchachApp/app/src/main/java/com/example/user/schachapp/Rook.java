package com.example.user.schachapp;

import java.util.List;

public class Rook extends Piece {
    private static final String ROOK_CHAR = "T";
    private static final int ROOK_VALUE = 5;
    public Rook(boolean isWhite) {
        super(isWhite, ROOK_VALUE, ROOK_CHAR);
    }
    @Override
    public List<Move> getMovement(Position position, BoardState board) {
        return null;
    }
}
