package com.example.user.schachapp.chessLogic;

/**
 * Represents a position on a chessboard, meaning both coordinates should be between 0-7
 */
public class Position {
    private int x;
    private int y;

    /**
     * Creates the position from given string representation. Checks if the position is correct.
     * @param representation the representation of the position
     */
    public Position(String representation) {
            if (representation == null || !representation.matches("[a-h][1-8]")) {
                throw new IllegalArgumentException("null argument or incorrect position representaion");
            } else {
                String alpha = "abcdefgh";
                String[] coords = representation.split("");
                if (coords[0].equals("")) {  //Strings can get split different on different devices with different java versions
                    coords[0] = coords[1];
                    coords[1] = coords[2];
                }
                int y = Integer.parseInt(coords[1]);
                this.x = alpha.indexOf(coords[0]);
                this.y = y - 1;
            }
    }

    /**
     * Creates the position from the given coordinates. Checks if the coordinates are correct.
     * @param x x-coordinate (horizontal)
     * @param y y-coordinate (vertical)
     */
    public Position(int x, int y) {
            if (x < 0 || x > 7 || y < 0 || y > 7) {
                throw new IllegalArgumentException("Position not on board");
            }
            this.x = x;
            this.y = y;
    }

    /**
     *
     * @return x-coordinate
     */
    public int getX() {
        return x;
    }

    /**
     *
     * @return y-coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Returns the string representation for a chess game of this position
     * by converting the x-coordinate to a letter and incrementing the y-coordinate by one
     * @return the string representation of this position
     */
    public String toString() {
        String alpha = "abcdefgh";
        return alpha.charAt(this.x) + String.valueOf(this.y + 1);
    }

    /**
     * Compares a given position to this one by comparing their coordinates.
     * @param compareTo the position to be compared
     * @return true if give position is equal to this one, false if not
     */
    public boolean equals(Position compareTo) {
        return ((compareTo.getY() == this.y) && (compareTo.getX() == this.x));
    }

}
