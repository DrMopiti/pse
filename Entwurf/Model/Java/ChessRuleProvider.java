package com.example.user.schachapp;

import java.util.List;

public class ChessRuleProvider extends RuleProvider {
    private BoardState start;
    private MoveValidator mover;
    private GameEnder ender;
    public ChessRuleProvider() {
        this.start = new ChessBoardState();
        this.mover = new ChessMoveValidator();
        this.ender = new ChessGameEnder();
    }

    @Override
    public BoardState getStartState() {
        return this.start;
    }

    @Override
    public boolean hasEnded(BoardState state) {
        return this.ender.hasEnded(state);
    }

    @Override
    public List<Move> getMoves(Position position, BoardState state) {
        return this.mover.getMoves(position, state);
    }
}
