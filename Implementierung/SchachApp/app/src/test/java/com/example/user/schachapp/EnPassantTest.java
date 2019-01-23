package com.example.user.schachapp;

import com.example.user.schachapp.chessLogic.EnPassant;
import com.example.user.schachapp.chessLogic.Move;
import com.example.user.schachapp.chessLogic.Position;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class EnPassantTest {

    public static EnPassant enPassant;

    @BeforeClass
    public static void setEnPassant(){
        enPassant = new EnPassant(new Position("e4"),new Position("d5"));
    }

    @Test
    public void positionFromRemovedPawn() {
        assertTrue(enPassant.getRemovePawn().equals(new Position("d4")));
    }

    @Test
    public void testRemovedPawn() {
        assertTrue(enPassant.getRemovePawn().equals(new Position("d4")));
    }

    @Test
    public void enPassantToString() {
        assertEquals(enPassant.toString(),"e4-d5-d4");
    }

    @Test
    public void equalsMoves() {
        assertTrue(enPassant.equals(new Move(new Position("e4"),new Position("d5"))));
    }
}