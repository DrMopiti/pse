package com.example.user.schachapp;

public class ChessGameEnder extends GameEnder {
    @Override
    public boolean hasEnded(BoardState state) {
        return true;
    }
}
