package com.example.user.schachapp;

public class BoardState {
    private Tile[][] tiles;
    private Move lastMove;
    private boolean whiteToMove;
    private boolean whiteKingCastle;
    private boolean whiteQueenCastle;
    private boolean blackKingCastle;
    private boolean blackQueenCastle;
    private int movesWithoutAction;

    public BoardState() {
        tiles = new Tile[8][8];
        lastMove = null;
        whiteKingCastle = true;
        whiteQueenCastle = true;
        blackKingCastle = true;
        blackQueenCastle = true;
        whiteToMove = true;
        movesWithoutAction = 0;
    }

    public BoardState(String string) {}

    public void applyMove(Move move) {}

    public boolean hasPieceAt(Position position) {return false;}

    public Piece getPieceAt(Position position) {
        return null;
    }

    public Move getLastMove() {
        return lastMove;
    }

    public boolean canWhiteKingCastle() {
        return whiteKingCastle;
    }

    public boolean canWhiteQueenCastle() {
        return whiteQueenCastle;
    }

    public boolean canBlackKingCastle() {
        return blackKingCastle;
    }

    public boolean canBlackQueenCastle() {
        return blackQueenCastle;
    }

    public int getMovesWithoutAction() {
        return movesWithoutAction;
    }

    public String toString() {return null;}

    public BoardState clone() {
        return null;
    }
}
