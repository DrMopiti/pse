package com.example.user.schachapp;

import com.example.user.schachapp.chessLogic.BoardState;
import com.example.user.schachapp.chessLogic.Move;
import com.example.user.schachapp.chessLogic.Position;
import com.example.user.schachapp.chessLogic.Queen;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class QueenTest {
    static Queen whiteQueen;
    static Queen blackQueen;
    static BoardState firstBoardState;
    static BoardState secondBoardState;

    @BeforeClass
    public static void setRook() {
        whiteQueen = new Queen(true);
        blackQueen = new Queen(false);
        firstBoardState = new BoardState("TB0000bt" +
                "SB0D00bs" +
                "00000Bbl" +
                "000Bb000" +
                "KB00d0bk" +
                "LB0B0sbl" +
                "SB00b000" +
                "TB0000bt" +
                "XXtttttX0");
        secondBoardState = new BoardState("0B0000b0" +
                "0B0000b0" +
                "K000D00k" +
                "T000B00t" +
                "0B00db00" +
                "00000sb0" +
                "SL0b0000" +
                "TB0L0b0T" +
                "XXtttttX0");
    }


    @Test
    public void getPossiblePositionsForWhiteQueenB4FirstBoard () {
        List<Move> possibleMoves = whiteQueen.getMovement(new Position("b4"), firstBoardState);
        assertTrue(possibleMoves.toString().equals("[b4-c5, b4-d6, b4-e7, b4-a3, b4-a5, b4-c3, b4-d2, b4-a4, b4-c4, b4-b3, b4-b5, b4-b6, b4-b7]"));
    }


    @Test
    public void getPossiblePositionsForBlackQueenE5FirstBoard () {
        List<Move> possibleMoves = blackQueen.getMovement(new Position("e5"), firstBoardState);
        assertTrue(possibleMoves.toString().equals("[e5-d4, e5-d6, e5-f4, e5-f5, e5-e4, e5-e3, e5-e2, e5-e6]"));
    }


    @Test
    public void getPossiblePositionsForWhiteQueenC5SecondBoard () {
        List<Move> possibleMoves = whiteQueen.getMovement(new Position("c5"), secondBoardState);
        assertTrue(possibleMoves.toString().equals("[c5-d6, c5-e7, c5-f8, c5-b4, c5-a3, c5-b6, c5-a7, c5-d4, c5-e3, c5-f2, c5-b5, c5-a5, c5-c4, c5-c3, c5-c2, c5-c6, c5-c7, c5-c8]"));
    }

     @Test
    public void getPossiblePositionsForBlackQueenE5SecondBoard () {
        List<Move> possibleMoves = blackQueen.getMovement(new Position("e5"), secondBoardState);
        assertTrue(possibleMoves.toString().equals("[e5-d4, e5-c3, e5-b2, e5-d6, e5-c7, e5-b8, e5-f4, e5-g3, e5-h2, e5-d5, e5-f5, e5-g5, e5-h5, e5-e4, e5-e3, e5-e2]"));
    }





}