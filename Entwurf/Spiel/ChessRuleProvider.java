package com.example.user.schachapp;

import java.util.List;

public class ChessRuleProvider implements RuleProvider {

    @Override
    public BoardState getStartState() {
        return null;
    }

    @Override
    public boolean isLegalMove(Move move, BoardState boardState) {
        return false;
    }

    @Override
    public List<Move> getLegalMoves(Position position, BoardState state) {
        return null;
    }

    @Override
    public boolean hasEnded(BoardState state) {
        return false;
    }

    @Override
    public Result getResult() {
        return null;
    }

    private List<Move> getPossibleMoves(Position position, BoardState state) {
        return state.getPieceAt(position).getMovement(position, state);
    }

    private boolean isChecked(boolean white, BoardState state) {
        return true;
    }
    private boolean isMate(BoardState state) {
        return false;
    }
    private boolean isStaleMate(BoardState state) {
        return false;
    }
    private boolean isDraw(BoardState state) {
        return false;
    }
}
