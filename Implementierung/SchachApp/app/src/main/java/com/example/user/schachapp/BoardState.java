package com.example.user.schachapp;

import java.util.ArrayList;
import java.util.List;

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

    public void applyMove(Move move) {

    }

    public boolean hasPieceAt(Position position) {
        return !tiles[position.getX()][position.getY()].getPiece().equals(null);
    }

    public Piece getPieceAt(Position position) {
        return tiles[position.getX()][position.getY()].getPiece();
    }

    public List<Position> getPiecesOfColor(boolean white) {
        List<Position> pieces = new ArrayList<>();
        for (int i = 0; i <= 7; i++) {
            for (int h = 0; h <= 7; h++) {
                if(tiles[i][h].getPiece().isWhite == white) {
                    pieces.add(new Position(i, h));
                }
            }
        }
        return pieces;
    }

    public Position getKingOfColor(boolean white) {
        for (int i = 0; i <= 7; i++) {
            for (int h = 0; h <= 7; h++) {
                if(tiles[i][h].getPiece().toString() == "K" && (tiles[i][h].getPiece().isWhite == white)) {
                    return new Position(i, h);
                }
            }
        }
        return null;
    }
    public Move getLastMove() {
        return lastMove;
    }

    public boolean whiteToMove() {
        return whiteToMove;
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
