package com.example.user.schachapp.chessLogic;

/**
 * A castling which extends move and consists of start, goal, and an additional rook move
 */
public class Castling extends Move {
    private Move rookMove;

    /**
     * Creates the castling with its given start and goal.
     * The rook move is calculated as it is unambiguous when the start and goal of the king is known.
     * @param start start position of the king
     * @param goal goal position of the king
     */
    public Castling(Position start, Position goal) {
        super(start, goal);
        Position rookStart, rookGoal;
        if(start.getX() < goal.getX()) {
            rookStart = new Position(7, start.getY());
            rookGoal = new Position(5, start.getY());
        } else {
            rookStart = new Position(0, start.getY());
            rookGoal = new Position(3, start.getY());
        }
        rookMove = new Move(rookStart, rookGoal);
    }

    /**
     *
     * @return the move of the rook
     */
    public Move getRookMove() {return rookMove; }

    /**
     * Returns the string representation of this move, which has an additional -C at the end.
     * @return string representation of this move
     */
    public String toString() {
        return (start.toString() + "-" + goal.toString() + "-C");
    }
}
