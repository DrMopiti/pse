package com.example.user.schachapp;

import com.example.user.schachapp.chessLogic.Castling;
import com.example.user.schachapp.chessLogic.Move;
import com.example.user.schachapp.chessLogic.Position;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class CastlingTest {
    public static Castling castling;

    @BeforeClass
    public static void setCastling() {
        castling = new Castling(new Position("e1"),new Position("g1"));
    }

    @Test
    public void testGetRookMove() {
        assertTrue(castling.getRookMove().equals(new Move(new Position("h1"),new Position("f1"))));
    }

    @Test
    public void testWhiteQueenCastle() {
        Castling tempCastling = new Castling(new Position("e1"), new Position("c1"));
        assertTrue(tempCastling.getRookMove().equals(new Move(new Position("a1"),new Position("d1"))));
    }

    @Test
    public void testBlackQueenCastle() {
        Castling tempCastling = new Castling(new Position("e8"), new Position("c8"));
        assertTrue(tempCastling.getRookMove().equals(new Move(new Position("a8"),new Position("d8"))));
        assertEquals(tempCastling.toString(),"e8-c8-C");
    }

    @Test
    public void castlingToString() {
        assertEquals(castling.toString(),"e1-g1-C"); // Castling wurde geändert
    }
}