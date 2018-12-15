package com.example.user.schachapp;

public class ChessGameEnder extends GameEnder {
    private Result result;
    @Override
    public boolean hasEnded(BoardState state) {
        return false;
    }
    public Result getResult() {
        return this.result;
    }
    private boolean checkForMate(BoardState state) {
        return false;
    }
    private boolean checkForStaleMate(BoardState state) {
        return false;
    }
    private boolean checkForDraw(BoardState state) {
        return false;
    }
}
