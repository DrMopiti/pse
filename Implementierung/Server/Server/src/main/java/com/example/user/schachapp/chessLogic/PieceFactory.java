package com.example.user.schachapp.chessLogic;

/**
 * A factory that can create a certain type of piece from a given string
 */
public class PieceFactory {

    /**
     * Creates the correct piece with correct color from the given string.
     * The toString method of the created piece should return the given string.
     * @param pieceRepresentation the string representation of the piece to be created
     * @return a piece of a certain type matching the given string
     */
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
