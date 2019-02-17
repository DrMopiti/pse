package com.example.user.schachapp.chessLogic;

/**
 * EnPassant, extends move and consists of start, goal and a position where a pawn has to be removed
 */
public class EnPassant extends Move {
    Position removePawn;

    /**
     * Creates the EnPassant with its given start and goal.
     * The removePawn position is calculated as it is unambiguous when the start and goal of the move is known.
     * @param start start position of the pawn
     * @param goal goal position of the pawn
     */
    public EnPassant(Position start, Position goal) {
        super(start, goal);
        removePawn = new Position(goal.getX(), start.getY());
    }

    /**
     *
     * @return the position to remove the pawn at
     */
    public Position getRemovePawn() {
        return removePawn;
    }

    /**
     * Returns the string representation of this move, which has an additional -E at the end.
     * @return string representation of this move
     */
    public String toString() {
        return (start.toString() + "-" + goal.toString() + "-E");
    }
}
