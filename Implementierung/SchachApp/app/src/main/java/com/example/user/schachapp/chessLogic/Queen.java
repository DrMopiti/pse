package com.example.user.schachapp.chessLogic;

import java.util.ArrayList;
import java.util.List;

import retrofit2.http.HEAD;

public class Queen extends Piece {
    private static final String QUEEN_CHAR = "D";
    private static final int QUEEN_VALUE = 9;
    public Queen(boolean isWhite) {
        super(isWhite, QUEEN_VALUE, QUEEN_CHAR);
    }
    @Override
    public  List<Move> getMovement(Position position, BoardState board) {

        List<Move> permittedMoves = new ArrayList<Move>();
        int yPosition = position.getY();
        int xPosition = position.getX();


        // permitted Moves main diagonal up
        for (int i = xPosition + 1, j = yPosition + 1; i <= 7 && j <= 7; i++, j++) {
            Position tempPosition = new Position(i, j);
            if (!board.hasPieceAt(tempPosition)) {
                permittedMoves.add(new Move(position, tempPosition));
            } else {
                if (board.getPieceAt(tempPosition).isWhite() != this.isWhite) {
                    permittedMoves.add(new Move(position, tempPosition));
                }
                break;
            }
        }

        // permitted Moves main diagonal down
        for (int i = xPosition - 1, j = yPosition - 1; i >= 0 && j >= 0; i--, j--) {
            Position tempPosition = new Position(i, j);
            if (!board.hasPieceAt(tempPosition)) {
                permittedMoves.add(new Move(position, tempPosition));
            } else {
                if (board.getPieceAt(tempPosition).isWhite() != this.isWhite) {
                    permittedMoves.add(new Move(position, tempPosition));
                }
                break;
            }
        }

        //permitted Moves neben diagonal up
        for (int i = xPosition - 1, j = yPosition + 1; i >= 0 && j <= 7; i--, j++) {
            Position tempPosition = new Position(i, j);
            if (!board.hasPieceAt(tempPosition)) {
                permittedMoves.add(new Move(position, tempPosition));
            } else {
                if (board.getPieceAt(tempPosition).isWhite() != this.isWhite) {
                    permittedMoves.add(new Move(position, tempPosition));
                }
                break;
            }
        }

        //permitted Moves neben diagonal down
        for (int i = xPosition + 1, j = yPosition - 1; i <=7 && j >= 0; i++, j--) {
            Position tempPosition = new Position(i, j);
            if (!board.hasPieceAt(tempPosition)) {
                permittedMoves.add(new Move(position, tempPosition));
            } else {
                if (board.getPieceAt(tempPosition).isWhite() != this.isWhite) {
                    permittedMoves.add(new Move(position, tempPosition));
                }
                break;
            }
        }

        // all possible positions left from position
        for (int i = xPosition - 1; i >= 0; i--) {
            Position tempPosition = new Position(i, position.getY());
            if (!board.hasPieceAt(tempPosition)) {
                permittedMoves.add(new Move(position, tempPosition));
            } else {
                if (board.getPieceAt(tempPosition).isWhite() != this.isWhite) {
                    permittedMoves.add(new Move(position, tempPosition));
                }
                break;
            }
        }

        // all possible positions right from position
        // lieber in BoardState ein static final Variable Definieren für feste Größe 7 für boardsize
        for (int i = xPosition + 1; i <= 7; i++) {
            Position tempPosition = new Position(i, position.getY());
            if (!board.hasPieceAt(tempPosition)) {
                permittedMoves.add(new Move(position, tempPosition));
            } else {
                if (board.getPieceAt(tempPosition).isWhite() != this.isWhite) {
                    permittedMoves.add(new Move(position, tempPosition));
                }
                break;
            }
        }

        // all possible positions down from position
        for (int j = yPosition - 1; j >= 0; j--) {
            Position tempPosition = new Position(position.getX(), j);
            if (!board.hasPieceAt(tempPosition)) {
                permittedMoves.add(new Move(position, tempPosition));
            } else {
                if (board.getPieceAt(tempPosition).isWhite() != this.isWhite) {
                    permittedMoves.add(new Move(position, tempPosition));
                }
                break;
            }
        }

        // all possible positions up from position
        for (int j = yPosition + 1; j <= 7; j++) {
            Position tempPosition = new Position(position.getX(), j);
            if (!board.hasPieceAt(tempPosition)) {
                permittedMoves.add(new Move(position, tempPosition));
            } else {
                if (board.getPieceAt(tempPosition).isWhite() != this.isWhite) {
                    permittedMoves.add(new Move(position, tempPosition));
                }
                break;
            }
        }
        return permittedMoves;
    }
}
