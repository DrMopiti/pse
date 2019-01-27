package com.example.user.schachapp;

import com.example.user.schachapp.chessLogic.Position;
import com.example.user.schachapp.chessLogic.Promotion;
import com.example.user.schachapp.chessLogic.Queen;

import org.junit.BeforeClass;
import org.junit.Test;

import java.net.PortUnreachableException;

import static org.junit.Assert.*;

public class PromotionTest {
    static Promotion promotion;

    @BeforeClass
    public static void setPromotion() {
        promotion = new Promotion(new Position(0,6), new Position(0,7),new Queen(true));
    }

    @Test
    public void getPromotion () {
        assertTrue(promotion.getPromotion() instanceof Queen);
    }

    @Test (expected = IllegalArgumentException.class)
    public void ifFalsePositionForStartOrGoalThen_IllegalArgumentExceptionThrown() {
       Promotion falsePromotion = new Promotion(new Position("a6"), new Position("a8"), new Queen(true));
    }

    @Test (expected = IllegalArgumentException.class)
    public void ifFalseColorOfPromotionThen_IllegalArgumentExceptionThrown() {
        Promotion falsePromotion = new Promotion(new Position("a2"), new Position("a1"), new Queen(true));
    }

    @Test
    public void promotionToString() {
        assertEquals(promotion.toString(),"a7-a8-D");
    }

}
