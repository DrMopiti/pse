package com.example.user.schachapp;

import java.util.List;

public class Queen extends Piece {
    private static final String QUEEN_CHAR = "D";
    private static final int QUEEN_VALUE = 9;
    public Queen(boolean isWhite) {
        super(isWhite, QUEEN_VALUE, QUEEN_CHAR);
    }
    @Override
    public  List<Move> getMovement(Position position, BoardState board) {
        return null;
    }
}
