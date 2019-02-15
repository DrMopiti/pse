package com.example.user.schachapp.chessLogic;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
    private static final String PAWN_CHAR = "B";
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

        //korrigiert war nicht vollständig richtig
        //straight Pawn Moves
     /*   int limit = (yPos == base) ? yPos + 2 * dir : yPos + dir;
        for (int j = yPos + dir ; isWhite ? j <= limit: j >= limit;) {
            Position tempPosition = new Position(xPos,j);
            if (!board.hasPieceAt(tempPosition)) {
                possibleMoves.add(new Move(position, tempPosition));
            }
            j = isWhite ? j + 1 : j - 1;
        }*/
    if( yPos + dir < 8 && yPos + dir >= 0) {
         Position tempPosition = new Position(xPos, yPos + dir);
         if (!board.hasPieceAt(tempPosition)) {
             possibleMoves.add(new Move(position, tempPosition));
               if (yPos == base) {
                    tempPosition = new Position(xPos, yPos + (2 * dir));
                 if (!board.hasPieceAt(tempPosition)) {
                     possibleMoves.add(new Move(position, tempPosition));
                   }
              }
         }
    }
        //capturing Pawn Moves (diagonal)

            if (xPos - dir >= 0 && xPos - dir <= 7 && yPos + dir >= 0 && yPos + dir <= 7) {
                Position diagPosLeft = new Position(xPos - dir, yPos + dir);
                if (board.hasPieceAt(diagPosLeft) && (board.getPieceAt(diagPosLeft).isWhite() != this.isWhite)) {
                    possibleMoves.add(new Move(position, diagPosLeft));
                }
            }

                // Kleine Fehler behoben
            if(xPos + dir >= 0 && xPos + dir <= 7 && yPos + dir >= 0 && yPos + dir <= 7) {
                Position diagPosRight = new Position(xPos + dir, yPos + dir);
                if (board.hasPieceAt(diagPosRight) && (board.getPieceAt(diagPosRight).isWhite() != this.isWhite)) {
                    possibleMoves.add(new Move(position, diagPosRight));
                }
            }
            // noch einige Fehler behoben
            //EnPassant
            if (yPos == base + 3 * dir) { // korrigiert war yPos = 4
                Move lastMove = board.getLastMove();
                Position lastMoved = lastMove.getGoal();
                if (board.getPieceAt(lastMoved).getValue() == 1) {
                    if (xPos - dir >= 0 && xPos - dir <= 7) {
                        Position possibleStartLeft = new Position(xPos - dir, yPos + 2 * dir);
                        Position possibleGoalLeft = new Position(xPos - dir, yPos);
                        if (lastMove.getStart().equals(possibleStartLeft) && lastMove.getGoal().equals(possibleGoalLeft)) {
                            possibleMoves.add(new EnPassant(position, new Position(xPos - dir, yPos + dir)));
                        }
                    } if(xPos + dir >= 0 && xPos + dir <= 7){
                        Position possibleStartRight = new Position(xPos + dir, yPos + 2 * dir);
                        Position possibleGoalRight = new Position(xPos + dir, yPos);
                        if (lastMove.getStart().equals(possibleStartRight) && lastMove.getGoal().equals(possibleGoalRight)) {
                            possibleMoves.add(new EnPassant(position, new Position(xPos + dir, yPos + dir)));
                        }
                    }
                }
            }

        return possibleMoves;
    }
}
