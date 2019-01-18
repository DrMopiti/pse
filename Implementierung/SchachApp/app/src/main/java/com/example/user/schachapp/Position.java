package com.example.user.schachapp;

public class Position {
    private int x;
    private int y;

    public Position(String representation) {
        String alpha = "abcdefgh";
        String[] coords = representation.split("");
        int y = Integer.parseInt(coords[1]);
      //  if (!alpha.contains(coords[0]) || (y > 7 || y < 0)) throw new IllegalPositionException();
      //  else {
            this.x = alpha.indexOf(coords[0]);
            this.y = y;
      //  }
    }
    public Position(int x, int y) {
       // if ((x > 7 || x < 0) || (y > 7 || y < 0)) throw new IllegalPositionException();
     //   else {
            this.x = x;
            this.y = y;
    //    }
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public String toString() {
        String alpha = "abcdefgh";
        Integer y = this.y;
        return alpha.charAt(this.x) + y.toString();
    }

    public boolean equals(Position compareTo) {
        return ((compareTo.getY() == y) && (compareTo.getX() == x));
    }

}
