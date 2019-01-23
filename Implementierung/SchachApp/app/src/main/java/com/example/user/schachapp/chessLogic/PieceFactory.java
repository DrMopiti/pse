package com.example.user.schachapp.com.example.user.schachapp.chessLogic;

import android.widget.ImageView;

import com.example.user.schachapp.BoardActivity;

public class PieceFactory {
    public static Piece getPiece(String pieceRepresentation, BoardActivity ba) {
        Piece p;
        ImageView iv = ba.getPieceIV(pieceRepresentation);
        switch (pieceRepresentation) {
            case "K":
                p = (new King(true, iv));
                break;
            case "k":
                p = (new King(false, iv));
                break;
            case "D":
                p = (new Queen(true, iv));
                break;
            case "d":
                p = (new Queen(false, iv));
                break;
            case "L":
                p = (new Bishop(true, iv));
                break;
            case "l":
                p = (new Bishop(false, iv));
                break;
            case "S":
                p = (new Knight(true, iv));
                break;
            case "s":
                p = (new Knight(false, iv));
                break;
            case "B":
                p = (new Pawn(true, iv));
                break;
            case "b":
                p = (new Pawn(false, iv));
                break;
            case "T":
                p = (new Rook(true, iv));
                break;
            case "t":
                p = (new Rook(false, iv));
                break;
            default:
                p = null;
        }
        return p;
    }
}
