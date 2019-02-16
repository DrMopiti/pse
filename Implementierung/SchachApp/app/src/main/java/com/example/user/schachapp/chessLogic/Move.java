package com.example.user.schachapp.chessLogic;

/**
 * A move, consisting of a start and a goal position.
 */
public class Move {
    protected Position start;
    protected Position goal;

    /**
     * Creates the move with its given start and goal.
     * @param start start position of the moving piece
     * @param goal goal position of the moving piece
     */
    public Move(Position start, Position goal) {
        if(start == null || goal == null){
            throw new NullPointerException("start or goal positions can not be null");
        }
        this.start = start;
        this.goal = goal;
    }

    /**
     *
     * @return the moves start position
     */
    public Position getStart() {
        return start;
    }

    /**
     *
     * @return the moves goal position
     */
    public Position getGoal() {
        return goal;
    }

    /**
     * Converts the move to a string by using the string representation of its positions and seperating them with a -.
     * @return string representation of this move
     */
    @Override
    public String toString() {
        return (start.toString() + "-" + goal.toString());
    }

    /**
     * Checks if a given move is equal to this one, by comparing their positions
     * @param compareTo the move to be compared
     * @return true if they are equal, false if not
     */
    public boolean equals(Move compareTo) {
        return (start.equals(compareTo.getStart()) && goal.equals(compareTo.getGoal()));
    }
}
