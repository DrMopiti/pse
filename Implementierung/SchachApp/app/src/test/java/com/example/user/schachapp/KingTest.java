package com.example.user.schachapp;

import com.example.user.schachapp.chessLogic.BoardState;
import com.example.user.schachapp.chessLogic.King;
import com.example.user.schachapp.chessLogic.Move;
import com.example.user.schachapp.chessLogic.Position;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class KingTest {

    static King whiteKing;
    static King blackKing;
    static BoardState firstBoard;
    static BoardState secondBoard;


    @BeforeClass
    public static void setKing () {
        whiteKing = new King(true);
        blackKing = new King(false);
        firstBoard = new BoardState("TB0000bt" +
                "0000Bb0s" +
                "0B0S00bl" +
                "0B0000b0" +
                "KD0Bb00k" +
                "0BS00bd0" +
                "0B000b00" +
                "Tb00b0L0" +
                "XXtttftX0");

        secondBoard = new BoardState("TB0000bt" +
                "0B000b0s" +
                "LBS000bl" +
                "0B00L00d" +
                "0K0Bb00k" +
                "0B000000" +
                "0B0DS000" +
                "TB00bl0t" +
                "XXtffftX0");
    }


    @Test
    public void possibleMovesForWhiteKingE1FirstBoard (){
        List<Move> possibleMoves = whiteKing.getMovement(new Position("e1"), firstBoard);
        assertTrue(possibleMoves.toString().equals("[e1-d1, e1-f1, e1-c1-C, e1-g1-C]"));
    }

    @Test
    public void possibleMovesForBlackKingE8FirstBoard (){
        List<Move> possibleMoves = blackKing.getMovement(new Position("e8"), firstBoard);
        assertTrue(possibleMoves.toString().equals("[e8-d8, e8-e7, e8-f8]"));
    }

    @Test
    public void possibleMovesForWhiteKingE2SecondBoard (){

        List<Move> possibleMoves = whiteKing.getMovement(new Position("e2"), secondBoard);
        assertTrue(possibleMoves.toString().equals("[e2-d1, e2-d3, e2-e1, e2-e3, e2-f1, e2-f3]"));
    }

    @Test
    public void possibleMovesForWhiteKingE8SecondBoard (){
        List<Move> possibleMoves = blackKing.getMovement(new Position("e8"), secondBoard);
        assertTrue(possibleMoves.toString().equals("[e8-d7, e8-e7, e8-f7, e8-f8]"));
    }

    @Test
    public void testForBlackKingCastleForE8() {
        BoardState board = new BoardState("TBS00sbt" +
                "0B000db0" +
                "00B0b000" +
                "0D0Bbl00" +
                "KBL00b0k" +
                "L0B0l0b0" +
                "S00B00b0" +
                "T00B0sbt" +
                "XXtttttX0");

        List<Move> possibleMoves = blackKing.getMovement(new Position("e8"), board);
        assertTrue(possibleMoves.toString().equals("[e8-d7, e8-d8, e8-e7, e8-f8, e8-g8-C, e8-c8-C]"));
    }

    @Test
    public void testForWhiteKingForE1() {
        BoardState board = new BoardState("TBS000bt" +
                "0B000db0" +
                "00B0b000" +
                "0s0Bbl00" +
                "KBL00b0k" +
                "L0B0l0b0" +
                "S00B00b0" +
                "T00B0sbt" +
                "XXtffttX0");

        List<Move> possibleMoves = whiteKing.getMovement(new Position("e1"), board);
        assertTrue(possibleMoves.toString().equals("[e1-d1, e1-d2, e1-f2]"));
    }

    @Test(expected = NullPointerException.class)
    public void throwsNullPointerException() {
        BoardState board = null;
        List<Move> possibleMoves = whiteKing.getMovement(new Position("e5"), board);
    }
}



