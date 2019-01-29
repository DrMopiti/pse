package com.example.user.schachapp;

import com.example.user.schachapp.chessLogic.*;

import org.junit.*;
import static org.junit.Assert.*;

import java.util.List;

public class PieceTest {
    public static Piece piece;

    @Before
    public void setPiece() {
        piece = new Queen(true);
    }

    @Test
    public  void color() {
        assertTrue(piece.isWhite());
        piece = new Queen(false);
        assertFalse(piece.isWhite());
    }

    @Test
    public void testValue() {
        assertEquals(piece.getValue(), 9);
    }

    @Test
    public void testChar() {
        assertEquals("D",piece.toString());
        piece = new Queen(false);
        assertEquals("d",piece.toString());
    }

    @Test
    public void testMovement() {
        BoardState board = new BoardState("TB0000bt" +
                "SB0000bs" +
                "LB0000bl" +
                "DB0000bd" +
                "KB0000bk" +
                "LB0000bl" +
                "SB0000bs" +
                "TB0000bt" +
                "##ttttt#0");
        List<Move> movement = piece.getMovement(new Position("d1"), board);
        assertTrue(movement.toString().equals("[]"));
    }
}
