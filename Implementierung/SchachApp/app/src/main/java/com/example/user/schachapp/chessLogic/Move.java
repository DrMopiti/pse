package com.example.user.schachapp.chessLogic;

public class Move {
    protected Position start;
    protected Position goal;

    public Move(Position start, Position goal) {
        if(start == null || goal == null){
            throw new NullPointerException("start or goal positions can not be null");
        }
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
