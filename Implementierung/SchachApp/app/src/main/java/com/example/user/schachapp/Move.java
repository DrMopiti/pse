package com.example.user.schachapp;

public class Move {
    protected Position start;
    protected Position goal;

    public Move(Position start, Position goal) {
        this.start = start;
        this.goal = goal;
    }

    public Position getStart() {
        return start;
    }

    public Position getGoal() {
        return goal;
    }

    @Override
    public String toString() {
        return start.toString() + "-" + goal.toString();
    }

    public boolean equals(Move compareTo) {
        return (start.equals(compareTo.getStart()) && goal.equals(compareTo.getGoal()));
    }
}
