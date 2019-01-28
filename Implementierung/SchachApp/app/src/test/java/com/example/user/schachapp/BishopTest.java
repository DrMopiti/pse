package com.example.user.schachapp;

import com.example.user.schachapp.chessLogic.Bishop;
import com.example.user.schachapp.chessLogic.BoardState;
import com.example.user.schachapp.chessLogic.Move;
import com.example.user.schachapp.chessLogic.Position;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class BishopTest {

    static Bishop whiteBishop;
    static Bishop blackBishop;
    static BoardState boardState;

    @BeforeClass
    public static void setBishop() {
        whiteBishop = new Bishop(true);
        blackBishop = new Bishop(false);
        boardState = new BoardState("TB0000bt" +
                "0B0l00bs" +
                "0l0L0b0l" +
                "K00b00b0" +
                "000B0000" +
                "0B0L00bt" +
                "0B00000k" +
                "TB0b00b0" +
                "##ttttt#0");
    }



    @Test
    public void possibleMovesForWhiteBishopC4 (){
        List<Move> possibleMoves = whiteBishop.getMovement(new Position("c4"), boardState);
        assertTrue(possibleMoves.toString().equals("[c4-d5, c4-e6, c4-f7, c4-b3, c4-b5, c4-a6, c4-d3, c4-e2, c4-f1]"));
    }

    @Test
    public void possibleMovesForBlackBishopC8 () {
        List<Move> possibleMoves = blackBishop.getMovement(new Position("c8"), boardState);
        assertEquals("[]",possibleMoves.toString());
    }

    @Test
    public void possibleMovesForWhiteBishopF4 () {
        List<Move> possibleMoves = whiteBishop.getMovement(new Position("f4"), boardState);
        assertEquals("[f4-g5, f4-h6, f4-e3, f4-d2, f4-c1, f4-e5, f4-d6, f4-c7, f4-b8, f4-g3]",possibleMoves.toString());
    }
    @Test
    public void possibleMovesForBlackBishopC2 () {
        List<Move> possibleMoves = blackBishop.getMovement(new Position("c2"), boardState);
        assertEquals("[c2-d3, c2-e4, c2-b1, c2-b3, c2-a4, c2-d1]",possibleMoves.toString());
    }

}