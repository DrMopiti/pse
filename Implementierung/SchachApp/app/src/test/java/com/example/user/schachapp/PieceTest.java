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
        assertEquals(piece.toString(), "D");
        piece = new Queen(false);
        assertEquals(piece.toString(), "d");
    }

    @Test
    public void testMovement() {
        BoardState board = new BoardState("TB0000btSB0000bsLB0000blDB0000bdKB0000bKLB0000blSB0000bsTB0000bt##ttttt#0");
        List<Move> movement = piece.getMovement(new Position("d4"), board);
        assertTrue(movement.isEmpty());
    }
}
