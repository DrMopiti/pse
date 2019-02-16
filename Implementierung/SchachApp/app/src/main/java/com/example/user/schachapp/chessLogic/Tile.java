package com.example.user.schachapp.chessLogic;

/**
 * Represents a square on a chessboard, which can contain a piece.
 */
public class Tile {
    private Piece piece;

    /**
     * Creates an empty tile with no piece.
     */
    public Tile() {
        this.piece = null;
    }

    /**
     * Creates a tile with given piece on it.
     * @param piece piece on this tile
     */
    public Tile(Piece piece) {
        this.piece = piece;
    }

    /**
     *
     * @return piece on this tile
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     * Sets this tiles piece to the given piece.
     * @param piece piece to be set on this tile
     */
    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    /**
     * Removes the piece on this tile by setting it to null.
     */
    public void removePiece() {
        this.piece = null;
    }
}
