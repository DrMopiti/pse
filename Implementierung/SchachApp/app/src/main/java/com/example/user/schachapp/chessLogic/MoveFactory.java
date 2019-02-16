package com.example.user.schachapp.chessLogic;

/**
 * A factory that can create a certain type of move from a given string
 */
public class MoveFactory {
    /**
     * Checks the given string for correctness and creates the correct move from it.
     * The toString method of the created move should return the given string.
     * @param moveString the string representation of the move to be created
     * @return a move of a certain type matching the given string
     */
    public static Move getMove(String moveString) {
        if (!moveString.contains("-") || moveString == null) {
            return null;
        }
        String[] moveParts = moveString.split("-");
        Position start = new Position(moveParts[0]);
        Position goal = new Position(moveParts[1]);

        if (start == null || goal == null || start.equals(goal)) {
            return null;
        }

        switch (moveParts.length)  {
            case 2:
                return new Move(start, goal);
            case 3:
               if (moveParts[2].length() != 1) {
                    return null;
               }
               if (moveParts[2].equals("E")) {
                   return new EnPassant(start, goal);
               }
               if (moveParts[2].equals("C")) {
                   return new Castling(start, goal);
               }
               Piece promotion = PieceFactory.getPiece(moveParts[2]);
               if (promotion == null || promotion.getValue() == 0 || promotion.getValue() == 1) {
                   return null;
               }
               return new Promotion(start, goal, promotion);
            default:
                return null;
        }
    }
}
