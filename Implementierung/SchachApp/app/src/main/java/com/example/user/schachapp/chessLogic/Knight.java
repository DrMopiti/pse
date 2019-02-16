package com.example.user.schachapp.chessLogic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a knight
 */
public class Knight extends Piece {
    private static final String KNIGHT_CHAR = "S";
    private static final int KNIGHT_VALUE = 3;
    public Knight(boolean isWhite) {
        super(isWhite, KNIGHT_VALUE, KNIGHT_CHAR);
    }

    /**
     *
     * @return filename for the image of this piece
     */
    @Override
    public String getImageName() {
        if (isWhite) {
            return "knight_figure_white";
        } else {
            return "knight_figure_black";
        }
    }

    /**
     * Calculates the movement of a piece on a given board for a given position and returns it as a list of moves.
     * The method will not check if there is the correct piece on the given position, it will also ignore if the piece is pinned.
     * @param position the position on which the piece stands
     * @param board the board on which the movement should be calculated
     * @return a list of moves
     */
    @Override
    public List<Move> getMovement(Position position, BoardState board) {
        if (board == null) {
            throw new NullPointerException("board or/and position can not be null");
        }

        int [][] offsets = {{-2,-1},{-2,1},{-1,2},{1,2},{2,1},{2,-1},{1,-2},{-1,-2}};
        ArrayList<Position> possiblePositions = new ArrayList<Position>();
        List <Move> possibleMoves = new ArrayList<Move>();

        int j = 0;
        for (int i = 0; i <= 7; i++) {
            try {
                Position tempPosition = new Position(position.getX() + offsets[i][j], position.getY() + offsets[i][j + 1]);
                possiblePositions.add(tempPosition);
            } catch (IllegalArgumentException e) {
                e.getMessage();
            }
        }

        for (Iterator<Position> iterator = possiblePositions.iterator(); iterator.hasNext();){
            Position goalPosition = iterator.next();
            if (!board.hasPieceAt(goalPosition)) {
                possibleMoves.add(new Move(position, goalPosition));
            } else if(board.getPieceAt(goalPosition).isWhite() != this.isWhite){
                possibleMoves.add(new Move(position,goalPosition ));
            }
        }
        return possibleMoves;
    }
}
