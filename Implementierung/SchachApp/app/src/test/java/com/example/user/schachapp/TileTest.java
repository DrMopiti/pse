package com.example.user.schachapp;

import com.example.user.schachapp.chessLogic.King;
import com.example.user.schachapp.chessLogic.Tile;

import org.junit.*;
import static org.junit.Assert.*;

public class TileTest {
    public static Tile tile;

    @Before
    public void setTile() {
        tile = new Tile(new King(true));
    }

    @Test
    public void testPiece() {
        assertTrue(tile.getPiece() instanceof King);
    }

    @Test
    public void removePiece() {
        tile.removePiece();
        assertNull(tile.getPiece());
    }

    @Test
    public void setNull() {
        tile.setPiece(null);
        assertNull(tile.getPiece());
    }

    @Test
    public void emptyTile() {
        tile = new Tile();
        assertNull(tile.getPiece());
    }
}
