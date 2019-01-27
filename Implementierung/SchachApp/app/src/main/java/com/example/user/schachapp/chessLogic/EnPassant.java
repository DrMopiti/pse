package com.example.user.schachapp.chessLogic;

public class EnPassant extends Move {
    Position removePawn;

    public EnPassant(Position start, Position goal) {
        super(start, goal);
        removePawn = new Position(goal.getX(), start.getY());
    }

    public Position getRemovePawn() {
        return removePawn;
    }

    public String toString() {
        return (start.toString() + "-" + goal.toString() + "-E");
    }

    public boolean equals(Move compareTo) {
        if (compareTo instanceof EnPassant == false){
            return false;
        }

        return (start.equals(compareTo.getStart()) && goal.equals(compareTo.getGoal()));
    }
}
