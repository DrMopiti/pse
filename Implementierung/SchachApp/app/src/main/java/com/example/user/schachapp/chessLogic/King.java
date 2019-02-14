package com.example.user.schachapp.chessLogic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class King extends Piece {
    private static final String KING_CHAR = "K";
    private static final int KING_VALUE = 0;
    public King(boolean isWhite) {
        super(isWhite, KING_VALUE, KING_CHAR);
    }

    @Override
    public String getImageName() {
        if (isWhite) {
            return "king_figure_white";
        } else {
            return "king_figure_black";
        }
    }

    @Override
    public List<Move> getMovement(Position position, BoardState board) {
        if (board == null) {
            throw new NullPointerException("board can not be null");
        }

        int [][] offsets = {{-1,-1},{-1,0},{-1,1},{0,-1},{0,1},{1,-1},{1,0},{1,1}};
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
                possibleMoves.add(new Move(position, goalPosition ));
            }
        }

        if (this.isWhite) {
            if (board.canWhiteQueenCastle()) {
                Position pos1 = new Position("b1");
                Position pos2 = new Position("c1");
                Position pos3 = new Position("d1");
                if(!board.hasPieceAt(pos1) && !board.hasPieceAt(pos2) && !board.hasPieceAt(pos3)) {
                    possibleMoves.add(new Castling(position, new Position("c1")));
                }
            }
            if (board.canWhiteKingCastle()){
                Position pos1 = new Position("f1");
                Position pos2 = new Position("g1");
                if(!board.hasPieceAt(pos1) && !board.hasPieceAt(pos2)) {
                    possibleMoves.add(new Castling(position, new Position("g1")));
                }
            }
        }
        else {
            if (board.canBlackKingCastle()) {
                Position pos1 = new Position("f8");
                Position pos2 = new Position("g8");
                if(!board.hasPieceAt(pos1) && !board.hasPieceAt(pos2)) {
                    possibleMoves.add(new Castling(position, new Position("g8")));
                }
            }
            if (board.canBlackQueenCastle()) {
                Position pos1 = new Position("b8");
                Position pos2 = new Position("c8");
                Position pos3 = new Position("d8");
                if(!board.hasPieceAt(pos1) && !board.hasPieceAt(pos2) && !board.hasPieceAt(pos3)) {
                    possibleMoves.add(new Castling(position, new Position("c8")));
                }
            }
        }
        return possibleMoves;
    }
}
