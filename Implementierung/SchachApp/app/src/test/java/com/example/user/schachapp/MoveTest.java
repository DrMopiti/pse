package com.example.user.schachapp;

import com.example.user.schachapp.chessLogic.Move;
import com.example.user.schachapp.chessLogic.Position;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class MoveTest {

    static Move move;

    @BeforeClass
    public static void setMove(){
        move = new Move(new Position("a3"),new Position(4,5));
    }

    @Test(expected = NullPointerException.class)
    public void expectedNullPointerException() {
        assertNotNull(new Move(null,new Position(2,3)));
    }
    @Test
    public void equalityOfStartPosition() {
        assertTrue(move.getStart().equals(new Position(0,2)));
    }

    @Test
    public void equalityOfGoalPosition() {
        assertTrue(move.getGoal().equals(new Position("e6")));
    }

    @Test
    public void moveToString() {
        assertEquals(move.toString(),"a3-e6");
    }

    @Test
    public void equalityOfMoves() {
        assertTrue(move.equals(new Move(new Position(0,2) , new Position("e6"))));
    }
}