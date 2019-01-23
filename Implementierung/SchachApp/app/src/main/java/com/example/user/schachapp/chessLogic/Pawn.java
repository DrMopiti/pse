package com.example.user.schachapp.chessLogic;

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

        int yPos = position.getY();
        int xPos = position.getX();
        int dir = isWhite ? 1 : -1;
        int base = isWhite ? 1 : 6;

        //straight Pawn Moves
        int limit = (yPos == base) ? yPos + 2*dir : yPos + dir;
        for (int j = yPos + dir ; j <= limit; j++) {
            Position tempPosition = new Position(xPos,j);
            if (!board.hasPieceAt(tempPosition)) {
                possibleMoves.add(new Move(position, tempPosition));
            }
        }

        //capturing Pawn Moves (diagonal)
        Position diagPosLeft = new Position(xPos + 1, yPos + dir);
        if (board.hasPieceAt(diagPosLeft) && (board.getPieceAt(diagPosLeft).isWhite != this.isWhite)) {
            possibleMoves.add(new Move(position, diagPosLeft));
        }

        Position diagPosRight = new Position(xPos - 1, yPos + dir);
        if (board.hasPieceAt(diagPosLeft) && (board.getPieceAt(diagPosLeft).isWhite != this.isWhite)) {
            possibleMoves.add(new Move(position, diagPosRight));
        }

        //EnPassant
        if (yPos == base + 4*dir) {
            Move lastMove = board.getLastMove();
            Position lastMoved = lastMove.getGoal();
            if (board.getPieceAt(lastMoved) instanceof Pawn) {
                Position possibleStartLeft = new Position(xPos - 1, yPos + 2*dir);
                Position possibleGoalLeft = new Position(xPos - 1, yPos);
                if (lastMove.getStart().equals(possibleStartLeft) && lastMove.getGoal().equals(possibleGoalLeft)) {
                    possibleMoves.add(new EnPassant(position, diagPosLeft));
                } else {
                    Position possibleStartRight = new Position(xPos + 1, yPos + 2 * dir);
                    Position possibleGoalRight = new Position(xPos + 1, yPos);
                    if (lastMove.getStart().equals(possibleStartRight) && lastMove.getGoal().equals(possibleGoalRight)) {
                        possibleMoves.add(new EnPassant(position, diagPosRight));
                    }
                }
            }
        }

        return possibleMoves;
    }
}
