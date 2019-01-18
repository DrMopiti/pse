package com.example.user.schachapp;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {
    private static final String ROOK_CHAR = "T";
    private static final int ROOK_VALUE = 5;
    public Rook(boolean isWhite) {
        super(isWhite, ROOK_VALUE, ROOK_CHAR);
    }
    @Override
    public List<Move> getMovement(Position position, BoardState board) {
        List<Move> permittedMoves = new ArrayList<Move>();

        int yPosition = position.getY();
        int xPosition = position.getX();

        // all possible positions left from position
        for (int i = xPosition - 1; i >= 0; i--) {
            Position tempPosition = new Position(i, position.getY());
            if (board.getPieceAt(tempPosition) == null) {
                permittedMoves.add(new Move(position, tempPosition));
            } else if (board.getPieceAt(tempPosition).isWhite() != this.isWhite) {
                permittedMoves.add(new Move(position, tempPosition));
                break;
            } else {
                break;
            }
        }

        // all possible positions right from position
        // lieber in BoardState ein static final Variable Definieren für feste Größe 7 für boardsize
        for (int i = xPosition + 1; i <= 7; i++) {
            Position tempPosition = new Position(i, position.getY());
            if (board.getPieceAt(tempPosition) == null) {
                permittedMoves.add(new Move(position, tempPosition));
            } else if (board.getPieceAt(tempPosition).isWhite() != this.isWhite) {
                permittedMoves.add(new Move(position, tempPosition));
                break;
            } else {
                break;
            }
        }

        // all possible positions down from position
        for (int j = yPosition - 1; j >= 0; j--) {
            Position tempPosition = new Position(position.getX(), j);
            if (board.getPieceAt(tempPosition) == null) {
                permittedMoves.add(new Move(position, tempPosition));
            } else if (board.getPieceAt(tempPosition).isWhite() != this.isWhite) {
                permittedMoves.add(new Move(position, tempPosition));
                break;
            } else {
                break;
            }
        }

        // all possible positions up from position
        for (int j = yPosition + 1; j <= 7; j++) {
            Position tempPosition = new Position(position.getX(), j);
            if (board.getPieceAt(tempPosition) == null) {
                permittedMoves.add(new Move(position, tempPosition));
            } else if (board.getPieceAt(tempPosition).isWhite() != this.isWhite) {
                permittedMoves.add(new Move(position, tempPosition));
                break;
            } else {
                break;
            }
        }

        // muss geprüft werden wegen Rochade , wie ich gelesen habe enPassant muss mit Bewegungen von Schach anfangen nicht von Rook
        /*
        if (this.isWhite == true && (board.canWhiteKingCastle() || board.canWhiteQueenCastle())) {
                permittedMoves.add(new Move(position, new Position(4,0)));
        }
        else if (this.isWhite == false && (board.canBlackQueenCastle() || board.canBlackKingCastle())) {
                permittedMoves.add(new Move(position, new Position(4,7)));
        }
        */
        return permittedMoves;
    }
}
