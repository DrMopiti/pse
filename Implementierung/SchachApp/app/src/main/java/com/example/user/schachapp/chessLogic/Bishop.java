package com.example.user.schachapp.com.example.user.schachapp.chessLogic;

import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {
    private static final String BISHOP_CHAR = "L";
    private static final int BISHOP_VALUE = 3;
    public Bishop(boolean isWhite, ImageView iv) {
        super(isWhite, BISHOP_VALUE, BISHOP_CHAR, iv);
    }
    @Override
    public List<Move> getMovement(Position position, BoardState board) {
        List<Move> permittedMoves = new ArrayList<Move>();
        int yPosition = position.getY();
        int xPosition = position.getX();

        // permitted Moves main diagonal up
        for (int i = xPosition + 1, j = yPosition + 1; i <= 7 && j <= 7; i++, j++) {
            Position tempPosition = new Position(i, j);
            if (!board.hasPieceAt(tempPosition)) {
                permittedMoves.add(new Move(position, tempPosition));
            } else if (board.getPieceAt(tempPosition).isWhite() != this.isWhite) {
                permittedMoves.add(new Move(position, tempPosition));
                break;
            } else {
                break;
            }
        }
        // permitted Moves main diagonal down
        for (int i = xPosition - 1, j = yPosition - 1; i >= 0 && j >= 0; i--, j--) {
            Position tempPosition = new Position(i, j);
            if (!board.hasPieceAt(tempPosition)) {
                permittedMoves.add(new Move(position, tempPosition));
            } else if (board.getPieceAt(tempPosition).isWhite() != this.isWhite) {
                permittedMoves.add(new Move(position, tempPosition));
                break;
            } else {
                break;
            }
        }
        //permitted Moves neben diagonal up
        for (int i = xPosition - 1, j = yPosition + 1; i >= 0 && j <= 7; i--, j++) {
            Position tempPosition = new Position(i, j);
            if (!board.hasPieceAt(tempPosition)) {
                permittedMoves.add(new Move(position, tempPosition));
            } else if (board.getPieceAt(tempPosition).isWhite() != this.isWhite) {
                permittedMoves.add(new Move(position, tempPosition));
                break;
            } else {
                break;
            }
        }

        //permitted Moves neben diagonal down
        for (int i = xPosition + 1, j = yPosition - 1; i <=7 && j >= 0; i++, j--) {
            Position tempPosition = new Position(i, j);
            if (!board.hasPieceAt(tempPosition)) {
                permittedMoves.add(new Move(position, tempPosition));
            } else if (board.getPieceAt(tempPosition).isWhite() != this.isWhite) {
                permittedMoves.add(new Move(position, tempPosition));
                break;
            } else {
                break;
            }
        }
        return permittedMoves;
    }
}