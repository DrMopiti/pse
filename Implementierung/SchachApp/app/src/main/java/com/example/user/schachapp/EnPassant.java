package com.example.user.schachapp;

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
        return start.toString() + "-" + goal.toString() + "-" + removePawn.toString();
    }
}
