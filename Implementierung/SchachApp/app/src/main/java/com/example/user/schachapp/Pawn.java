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
        int dir = isWhite ? 1 : -1;
        int base = isWhite ? 1 : 6;

        //straight Pawn Moves
        int limit = (yPosition == base) ? yPosition + 2*dir : yPosition + dir;
        for (int j = yPosition + dir ; j <= limit; j++) {
            Position tempPosition = new Position(position.getX(),j);
            if (!board.hasPieceAt(tempPosition)) {
                possibleMoves.add(new Move(position, tempPosition));
            }
        }

        //diagonal Pawn Moves


        //EnPassant
        if (yPosition == base + 4*dir) {

        }

        return possibleMoves;
    }
}
