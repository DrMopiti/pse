package com.example.user.schachapp;

import com.example.user.schachapp.chessLogic.Bishop;
import com.example.user.schachapp.chessLogic.BoardState;
import com.example.user.schachapp.chessLogic.King;
import com.example.user.schachapp.chessLogic.Knight;
import com.example.user.schachapp.chessLogic.Move;
import com.example.user.schachapp.chessLogic.Position;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class KnightTest {
    static Knight whiteKnight;
    static Knight blackKnight;
    static BoardState boardState;

    @BeforeClass
    public static void setBishop() {
        whiteKnight = new Knight(true);
        blackKnight = new Knight(false);
        boardState = new BoardState("TB0d00bt" +
                "KB00b000" +
                "L0SB00b0" +
                "0Bs0b000" +
                "000BS0bk" +
                "LB0Dl0bl" +
                "0B0s00b0" +
                "TB00b00t" +
                "##tfftt#0");
    }

    @Test
    public void possibleMovesForWhiteKnightC3(){
        List<Move> possibleMoves = whiteKnight.getMovement(new Position("c3"), boardState);
        assertTrue(possibleMoves.toString().equals("[c3-a4, c3-b5, c3-d5, c3-e2, c3-d1]"));
    }

    @Test
    public void possibleMovesForWhiteKnightE5(){
        List<Move> possibleMoves = whiteKnight.getMovement(new Position("e5"), boardState);
        assertTrue(possibleMoves.toString().equals("[e5-c6, e5-d7, e5-f7, e5-g6, e5-g4, e5-f3, e5-d3]"));
    }

    @Test
    public void possibleMovesForBlackKnightD3(){
        List<Move> possibleMoves = blackKnight.getMovement(new Position("d3"), boardState);
        assertTrue(possibleMoves.toString().equals("[d3-b2, d3-b4, d3-c5, d3-e5, d3-f4, d3-f2, d3-e1, d3-c1]"));
    }

    @Test
    public void possibleMovesForBlackKnightG4(){
        List<Move> possibleMoves = blackKnight.getMovement(new Position("g4"), boardState);
        assertTrue(possibleMoves.toString().equals("[g4-e3, g4-e5, g4-f6, g4-h6, g4-h2, g4-f2]"));
    }

    @Test(expected = NullPointerException.class)
    public void throwsNullPointerExceptionIfBoardNull() {
        BoardState board = null;
        List<Move> possibleMoves = whiteKnight.getMovement(new Position("b2"), board);
    }
}
