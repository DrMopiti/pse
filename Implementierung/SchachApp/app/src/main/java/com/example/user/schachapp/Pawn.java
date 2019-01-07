package com.example.user.schachapp;

import java.util.List;

public class Pawn extends Piece {
    private static final String PAWN_CHAR = "";
    private static final int PAWN_VALUE = 1;
    public Pawn(boolean isWhite) {
        super(isWhite, PAWN_VALUE, PAWN_CHAR);
    }
    @Override
    public List<Move> getMovement(Position position, BoardState board) {
        return null;
    }
}
