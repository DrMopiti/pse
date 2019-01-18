package com.example.user.schachapp;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
    private static final String PAWN_CHAR = "";
    private static final int PAWN_VALUE = 1;
    public Pawn(boolean isWhite) {
        super(isWhite, PAWN_VALUE, PAWN_CHAR);
    }
    @Override
    public List<Move> getMovement(Position position, BoardState board) {
        ArrayList<Position> possiblePositions = new ArrayList<Position>();
        List <Move> possibleMoves = new ArrayList<Move>();

        int yPosition = position.getY();
        int xPosition = position.getX();


        if (this.isWhite == true){
            if (yPosition >= 1 && yPosition < 7) {
                int limit = yPosition == 1 ? yPosition + 2 : yPosition + 1;
                for (int j = yPosition + 1 ; j <= limit; j++) {
                    Position tempPosition = new Position(position.getX(),j);
                    if (board.getPieceAt(tempPosition) == null) {
                        possibleMoves.add(new Move(position, tempPosition));
                    } else if (board.getPieceAt(tempPosition).isWhite() == false) {
                        possibleMoves.add(new Move(position, tempPosition));
                        break;
                    } else {
                        break;
                    }
                }
            }
        }

        else if (this.isWhite == false)
        {
            if (yPosition > 1 && yPosition <= 7) {
                int limit = yPosition == 6 ? yPosition - 2 : yPosition - 1;
                for (int j = yPosition - 1 ; j <= limit; j--) {
                    Position tempPosition = new Position(position.getX(),j);
                    if (board.getPieceAt(tempPosition) == null) {
                        possibleMoves.add(new Move(position, tempPosition));
                    } else if (board.getPieceAt(tempPosition).isWhite() == true) {
                        possibleMoves.add(new Move(position, tempPosition));
                        break;
                    } else {
                        break;
                    }
                }
            }
        }
        return possibleMoves;
    }
}
