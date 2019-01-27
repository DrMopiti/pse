package com.example.user.schachapp;

import com.example.user.schachapp.chessLogic.BoardState;
import com.example.user.schachapp.chessLogic.Move;
import com.example.user.schachapp.chessLogic.Position;
import com.example.user.schachapp.chessLogic.Queen;
import com.example.user.schachapp.chessLogic.Rook;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class QueenTest {
    static Queen whiteQueen;
    static Queen blackQueen;
    static BoardState boardState;

    @BeforeClass
    public static void setRook() {
        whiteQueen = new Queen(true);
        blackQueen = new Queen(false);
        boardState = new BoardState("TB0000bt" +
                "SB0D00bs" +
                "00000Bbl" +
                "000Bb000" +
                "KB00d0bk" +
                "LB0B0sbl" +
                "SB00b000" +
                "TB0000bt" +
                "##ttttt#0");
    }

    @Test
    public void getPossiblePositionsForWhiteQueenB4 () {
        List<Move> possibleMoves = whiteQueen.getMovement(new Position("b4"),boardState);
        assertTrue(possibleMoves.toString().equals("[b4-c5, b4-d6, b4-e7, b4-a3, b4-a5, b4-c3, b4-d2, b4-a4, b4-c4, b4-b3, b4-b5, b4-b6, b4-b7]"));
    }

    @Test
    public void getPossiblePositionsForBlackQueenE5 () {
        List<Move> possibleMoves = blackQueen.getMovement(new Position("e5"),boardState);
        assertTrue(possibleMoves.toString().equals("[e5-d4, e5-d6, e5-f4, e5-f5, e5-e4, e5-e3, e5-e2, e5-e6]"));
    }


}