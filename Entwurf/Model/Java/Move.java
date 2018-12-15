package com.example.user.schachapp;

public class Move {
    private Position start;
    private Position goal;
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
}
