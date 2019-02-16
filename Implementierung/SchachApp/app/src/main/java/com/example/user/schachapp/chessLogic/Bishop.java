package com.example.user.schachapp.chessLogic;

import java.util.ArrayList;
import java.util.List;

/**
 * a bishop
 */
public class Bishop extends Piece {
    private static final String BISHOP_CHAR = "L";
    private static final int BISHOP_VALUE = 3;
    public Bishop(boolean isWhite) {
        super(isWhite, BISHOP_VALUE, BISHOP_CHAR);
    }

    /**
     *
     * @return filename for the image of this piece
     */
    @Override
    public String getImageName() {
        if (isWhite) {
            return "bishop_figure_white";
        } else {
            return "bishop_figure_black";
        }
    }

    /**
     * Calculates the movement of a piece on a given board for a given position and returns it as a list of moves.
     * The method will not check if there is the correct on the given position, it will also ignore if the piece is pinned.
     * @param position the position on which the piece stands
     * @param board the board on which the movement should be calculated
     * @return a list of moves
     */
    @Override
    public List<Move> getMovement(Position position, BoardState board) {
        if (board == null) {
            throw new NullPointerException("board can not be null");
        }
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
        for (int i = xPosition + 1, j = yPosition - 1; i <= 7 && j >= 0; i++, j--) {
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
        return permittedMoves;
    }
}
