package com.example.user.schachapp;

public class ChessBoardState extends BoardState {
    private Tile tiles[][];
    private Move lastMove;
    private boolean whiteKingCastle;
    private boolean whiteQueenCastle;
    private boolean blackKingCastle;
    private boolean blackQueenCastle;
    private boolean whiteToMove;

    public ChessBoardState() {
        Tile tiles[][] = new Tile[8][8];
        lastMove = null;
        whiteKingCastle = true;
        whiteQueenCastle = true;
        blackKingCastle = true;
        blackQueenCastle = true;
        whiteToMove = true;
        //Do the Startposition here
    }

    public ChessBoardState(String s) {

    }

    public void applyMove(Move move) {
    }

    public Piece getPieceAt(Position p) {
        return null;
    }

    public void setPieceAt(Position p, Piece piece) {

    }

    public Move getLastMove() {
        return lastMove;
    }

    public void setLastMove(Move lastMove) {
        this.lastMove = lastMove;
    }

    public boolean canWhiteKingCastle() {
        return whiteKingCastle;
    }

    public void setWhiteKingCastle(boolean whiteKingCastle) {
        this.whiteKingCastle = whiteKingCastle;
    }

    public boolean canWhiteQueenCastle() {
        return whiteQueenCastle;
    }

    public void setWhiteQueenCastle(boolean whiteQueenCastle) {
        this.whiteQueenCastle = whiteQueenCastle;
    }

    public boolean canBlackKingCastle() {
        return blackKingCastle;
    }

    public void setBlackKingCastle(boolean blackKingCastle) {
        this.blackKingCastle = blackKingCastle;
    }

    public boolean canBlackQueenCastle() {
        return blackQueenCastle;
    }

    public void setBlackQueenCastle(boolean blackQueenCastle) {
        this.blackQueenCastle = blackQueenCastle;
    }

    public String toString() {
        return null;
    }
}
