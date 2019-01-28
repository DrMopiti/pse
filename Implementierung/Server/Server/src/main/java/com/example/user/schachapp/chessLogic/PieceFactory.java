package com.example.user.schachapp.chessLogic;

public class PieceFactory {
    public static Piece getPiece(String pieceRepresentation) {
        switch (pieceRepresentation) {
            case "K":
                return new King(true);
            case "k":
                return new King(false);
            case "D":
                return new Queen(true);
            case "d":
                return new Queen(false);
            case "L":
                return new Bishop(true);
            case "l":
                return new Bishop(false);
            case "S":
                return new Knight(true);
            case "s":
                return new Knight(false);
            case "B":
                return new Pawn(true);
            case "b":
                return new Pawn(false);
            case "T":
                return new Rook(true);
            case "t":
                return new Rook(false);
            default:
                return null;
        }
    }
}
