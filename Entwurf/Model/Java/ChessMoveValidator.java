package com.example.user.schachapp;

import java.util.List;

public class ChessMoveValidator extends MoveValidator {
    @Override
    public List<Move> getLegalMoves(Position position, BoardState state) {
        return null;
    }
    public boolean verifyMove(Move move, BoardState state) {
        return true;
    }
    private List<Move> getPossibleMoves(Position position, BoardState state) {
        return null;
    }
    private boolean isChecked(boolean white, ChessBoardState state) {
        return true;
    }
}
