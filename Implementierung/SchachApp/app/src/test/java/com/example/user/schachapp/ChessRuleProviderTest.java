package com.example.user.schachapp;

import com.example.user.schachapp.chessLogic.BoardState;
import com.example.user.schachapp.chessLogic.ChessRuleProvider;
import com.example.user.schachapp.chessLogic.Position;

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
    public void legalMovesForE8(){
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




}
