package com.example.user.schachapp;

import com.example.user.schachapp.chessLogic.Position;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class PositionTest {

    static Position firstConsPosition;
    static Position secondConsPosition;

    @BeforeClass
    public static void setUp(){
        firstConsPosition = new Position("b6");
        secondConsPosition = new Position(3,5);
    }

    @Test
    public void getXFromFirstConsPosition() {
        assertEquals(firstConsPosition.getX(),1);
    }

    @Test
    public void getYFromFirstConsPosition() {
        assertEquals(firstConsPosition.getY(), 6);
    }

    @Test
    public void toStringFromSecondConsPosition() {
        assertEquals(secondConsPosition.toString(),"d5");
    }

    @Test
    public void notEqualityOfPositions() {
        assertFalse(firstConsPosition.equals(secondConsPosition));
    }

    @Test
    public void equalityOfPositions() {
        Position equalFirstConsPosition = new Position(1,6);
        assertTrue(firstConsPosition.equals(equalFirstConsPosition));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testIllegalArgumentException (){
        assertNull(new Position(null));
    }
}