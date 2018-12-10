package com.example.user.schachapp;

import java.util.List;

public class ChessMoveValidator extends MoveValidator {
    @Override
    public List<Move> getMoves(Position position, BoardState state) {
        return null;
    };
    private boolean verifyMove(Move move, BoardState state) {
        return true;
    }
    private boolean isChecked(boolean white, ChessBoardState state) {
        return true;
    }
}
