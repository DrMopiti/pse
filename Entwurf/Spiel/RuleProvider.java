package com.example.user.schachapp;

import java.util.List;

public interface RuleProvider {
    BoardState getStartState();
    List<Move> getLegalMoves(Position position, BoardState boardState);
    boolean isLegalMove(Move move, BoardState boardState);
    boolean hasEnded(BoardState state);
    Result getResult();
}
