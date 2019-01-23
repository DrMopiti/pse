package com.example.user.schachapp.com.example.user.schachapp.chessLogic;

import com.example.user.schachapp.BoardActivity;

public class MoveFactory {
    public static Move getMove(String moveString, BoardActivity ba) {
        Move move = null;
        String[] moveParts = moveString.split("-");
        Position start = new Position(moveParts[0]);
        Position goal = new Position(moveParts[1]);
        switch (moveParts.length)  {
            case 2:
                move = new Move(start, goal);
                break;
            case 3:
                if (moveParts[2].length() == 1) {
                    move = new Promotion(start, goal, PieceFactory.getPiece(moveParts[2], ba));
                } else if (moveParts[2].length() == 2) {
                    move = new EnPassant(start, goal);
                }
                break;
            case 4:
                move = new Castling(start, goal);
                break;
            default:
                move = null;
        }
        return move;
    }
}