package com.example.user.schachapp;

import com.example.user.schachapp.chessLogic.BoardState;
import com.example.user.schachapp.chessLogic.ChessRuleProvider;
import com.example.user.schachapp.chessLogic.MoveFactory;
import com.example.user.schachapp.chessLogic.Position;
import com.example.user.schachapp.chessLogic.Result;

import org.junit.BeforeClass;
import org.junit.Test;

import org.junit.*;

import static org.junit.Assert.*;


public class ChessRuleProviderTest {
    public static ChessRuleProvider ruleProvider;
    public static BoardState board;


    @BeforeClass
    public static void setRuleProvider() {
        ruleProvider = new ChessRuleProvider();
        board = new BoardState("0B0000b0" +
                "0LB00bl0" +
                "0t000000" +
                "0D00B000" +
                "00Tbb0dk" +
                "T0000000" +
                "KB000000" +
                "0B0000Bt" +
                "##tfftf#0");
    }

  /*  @Test
    public void legalMovesForD2(){
        assertEquals("[d2-c1, d2-c3, d2-b4, d2-a5, d2-e1, d2-c2, d2-e2, d2-f2, d2-d1, d2-d3, d2-d4]",ruleProvider.getLegalMoves(new Position("d2"),board).toString());
       // ruleProvider.getLegalMoves(new Position("d2"),board);
    }
    */

    @Test
    public void testLegalMoves(){
       BoardState testBoard = new BoardState("0B0000b0" +
                "0LB00bl0" +
                "0t000000" +
                "0D00B000" +
                "00Tbb0dk" +
                "00000000" +
                "KB000000" +
                "0B00L00t" +
                "#d1-h5#ffftf#0");

        assertEquals("[e8-d7, e8-d8, e8-f8]",ruleProvider.getLegalMoves(new Position("e8"),testBoard).toString());
        
    }

    @Test
    public void testStartState() {
        BoardState testBoard = ruleProvider.getStartState();
        assertEquals(testBoard.toString(), "TB0000btSB0000bsLB0000blDB0000bdKB0000bkLB0000blSB0000bsTB0000bt##ttttt#0");
    }

    @Test
    public void testHasEnded() {
        BoardState testBoard = ruleProvider.getStartState();
        assertFalse(ruleProvider.hasEnded(testBoard));
        testBoard.applyMove(MoveFactory.getMove("e2-e4"));
        testBoard.applyMove(MoveFactory.getMove("e7-e5"));
        testBoard.applyMove(MoveFactory.getMove("d1-h5"));
        testBoard.applyMove(MoveFactory.getMove("e8-e7"));
        testBoard.applyMove(MoveFactory.getMove("h5-e5"));
        assertTrue(ruleProvider.hasEnded(testBoard));
    }

    @Test
    public void testGetResult() {
        BoardState testBoard = ruleProvider.getStartState();
        assertNull(ruleProvider.getResult(testBoard));
        testBoard.applyMove(MoveFactory.getMove("e2-e4"));
        testBoard.applyMove(MoveFactory.getMove("e7-e5"));
        testBoard.applyMove(MoveFactory.getMove("d1-h5"));
        testBoard.applyMove(MoveFactory.getMove("e8-e7"));
        testBoard.applyMove(MoveFactory.getMove("h5-e5"));
        assertEquals(ruleProvider.getResult(testBoard).getResult(), "1:0");
        assertEquals(ruleProvider.getResult(testBoard).getReason(), "Matt");
    }
}
