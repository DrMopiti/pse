package com.example.user.schachapp;

import com.example.user.schachapp.chessLogic.King;
import com.example.user.schachapp.chessLogic.Piece;
import com.example.user.schachapp.chessLogic.PieceFactory;

import org.junit.*;
import static org.junit.Assert.*;

public class PieceFactoryTest {
    public static Piece piece;

    @Test
    public void pieceColor() {
        piece = PieceFactory.getPiece("k");
        assertTrue(piece instanceof King);
        assertFalse(piece.isWhite());
    }

    @Test
    public void wrongInput() {
        piece = PieceFactory.getPiece("r");
        assertNull(piece);
    }
}
