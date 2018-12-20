package com.example.user.schachapp;

public class Position {
    private int x;
    private int y;

    public Position(String representation) throws IllegalPositionException {

    }
    public Position(int x, int y) throws IllegalPositionException {
        if ((x > 7 || x < 0) || (y > 7 || y < 0)) throw new IllegalPositionException();
        else {
            this.x = x;
            this.y = y;
        }
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public String toString() {
        return null;
    }

}
