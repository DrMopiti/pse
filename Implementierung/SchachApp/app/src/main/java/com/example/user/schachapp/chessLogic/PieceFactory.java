package com.example.user.schachapp.chessLogic;

public class PieceFactory {
    public static Piece getPiece(String pieceRepresentation) {
        Piece p;
        switch (pieceRepresentation) {
            case "K":
                p = (new King(true));
                break;
            case "k":
                p = (new King(false));
                break;
            case "D":
                p = (new Queen(true));
                break;
            case "d":
                p = (new Queen(false));
                break;
            case "L":
                p = (new Bishop(true));
                break;
            case "l":
                p = (new Bishop(false));
                break;
            case "S":
                p = (new Knight(true));
                break;
            case "s":
                p = (new Knight(false));
                break;
            case "B":
                p = (new Pawn(true));
                break;
            case "b":
                p = (new Pawn(false));
                break;
            case "T":
                p = (new Rook(true));
                break;
            case "t":
                p = (new Rook(false));
                break;
            default:
                p = null;
        }
        return p;
    }
}
