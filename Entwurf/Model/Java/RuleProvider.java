package com.example.user.schachapp;

import java.util.List;

public abstract class RuleProvider {
    public abstract BoardState getStartState();
    public abstract boolean hasEnded(BoardState state);
    public abstract List<Move> getMoves(Position position, BoardState state);
}
