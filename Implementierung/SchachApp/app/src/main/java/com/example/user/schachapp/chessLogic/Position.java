package com.example.user.schachapp.chessLogic;

public class Position {
    private int x;
    private int y;

    public Position(String representation) {

        if (representation == null || !representation.matches("[a-h][1-8]")) {
            throw new IllegalArgumentException("übergebene object ist entweder null oder "
                    + "representation passt nicht vorgaben");
        }

        String alpha = "abcdefgh";
        String[] coords = representation.split("");
        int y = Integer.parseInt(coords[1]);
        this.x = alpha.indexOf(coords[0]);
        this.y = y - 1;
    }
    public Position(int x, int y) {
        if (x < 0 || x > 7 || y < 0 || y > 7) {
            throw new IllegalArgumentException("Position not on board");
        }
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public String toString() {
        String alpha = "abcdefgh";
        return alpha.charAt(this.x) + String.valueOf(this.y + 1);
    }

    public boolean equals(Position compareTo) {
        return ((compareTo.getY() == this.y) && (compareTo.getX() == this.x));
    }

}
