package com.example.user.schachapp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Knight extends Piece {
    private static final String KNIGHT_CHAR = "S";
    private static final int KNIGHT_VALUE = 3;
    public Knight(boolean isWhite) {
        super(isWhite, KNIGHT_VALUE, KNIGHT_CHAR);
    }
    @Override
    public List<Move> getMovement(Position position, BoardState board) {
        int [][] offsets = {{-2,-1},{-2,1},{-1,2},{1,2},{2,1},{2,-1},{1,-2},{-1,-2}};
        ArrayList<Position> possiblePositions = new ArrayList<Position>();
        List <Move> possibleMoves = new ArrayList<Move>();

        int j = 0;
        for (int i = 0; i <= 7; i++) {
            possiblePositions.add(new Position(position.getX() + offsets[i][j], position.getY() + offsets[i][j + 1]));
        }

        for (Iterator<Position> iterator = possiblePositions.iterator(); iterator.hasNext();){
            Position goalPosition = iterator.next();
            if(board.getPieceAt(goalPosition).isWhite() != this.isWhite){
                possibleMoves.add(new Move(position,goalPosition ));
            }
            else if (board.getPieceAt(goalPosition) == null) {
                possibleMoves.add(new Move(position, goalPosition));
            }
        }
        return possibleMoves;
    }
}
