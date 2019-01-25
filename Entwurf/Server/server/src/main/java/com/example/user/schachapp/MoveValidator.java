package com.example.user.schachapp;

import java.util.List;

public abstract class MoveValidator {
    public abstract List<Move> getLegalMoves(Position position, BoardState state);
}
