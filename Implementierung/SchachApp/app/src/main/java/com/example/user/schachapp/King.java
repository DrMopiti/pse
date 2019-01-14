package com.example.user.schachapp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class King extends Piece  {
    private static final String KING_CHAR = "K";
    private static final int KING_VALUE = 0;
    public King(boolean isWhite) {
        super(isWhite, KING_VALUE, KING_CHAR);
    }
    @Override
    public List<Move> getMovement(Position position, BoardState board) throws IllegalPositionException {

        int [][] offsets = {{-1,-1},{-1,0},{-1,1},{0,-1},{0,1},{1,-1},{1,0},{1,1}};
        ArrayList<Position> possiblePositions = new ArrayList<Position>();
        List <Move> possibleMoves = new ArrayList<Move>();

        int j = 0;
        for (int i = 0; i<=7; i++) {
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

        if (this.isWhite == true && (board.canWhiteKingCastle() || board.canWhiteQueenCastle())) {
            if (board.canWhiteQueenCastle()) {
                possibleMoves.add(new Move(position, new Position(2, 0)));
            }
            if (board.canWhiteKingCastle()){
                possibleMoves.add(new Move(position, new Position(6, 0)));
            }
        }
        else if (this.isWhite == false && (board.canBlackQueenCastle() || board.canBlackKingCastle())) {
            if (board.canBlackKingCastle()) {
                possibleMoves.add(new Move(position, new Position(6, 7)));
            }
            if (board.canBlackQueenCastle()) {
                possibleMoves.add(new Move(position, new Position(2, 7)));
            }
        }
        return possibleMoves;
    }
}
