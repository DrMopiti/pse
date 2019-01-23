package com.example.user.schachapp.chessLogic;

public class Castling extends Move {
    private Move rookMove;
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

    public Move getRookMove() {return rookMove; }

    public String toString() {
        return start.toString() + "-" + goal.toString() + "-" + rookMove.getStart().toString() + "-" + rookMove.getGoal().toString();
    }
}
