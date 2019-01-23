package com.example.user.schachapp.chessLogic;



import java.util.List;

public abstract class Piece {
    
    protected boolean isWhite;
    protected int value;
    protected String representation;
    protected Piece(boolean isWhite, int value, String representation) {
        this.isWhite = isWhite;
        this.value = value;
        this.representation = representation;
    }
    public String toString() {
        if (isWhite) {
            return representation;
        } else {
            return representation.toLowerCase();
        }
    };
    public int getValue() {
        return value;
    }
   
    public boolean isWhite() {
        return isWhite;
    };
    public abstract List<Move> getMovement(Position position, BoardState board);
}
