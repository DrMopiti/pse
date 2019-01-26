package com.example.user.schachapp;

import com.example.user.schachapp.chessLogic.BoardState;
import com.example.user.schachapp.chessLogic.MoveFactory;


import org.junit.*;

import static org.junit.Assert.*;

public class BoardStateTest {
    public static BoardState board;

    @Before
    public void setBoard() {
        board = new BoardState("TB0000btSB0000bsLB0000blDB0000bdKB0000bKLB0000blSB0000bsTB0000bt##ttttt#0");
    }

    @Test
    public void applyMove() {
        board.applyMove(MoveFactory.getMove("e2-e4"));
        assertEquals(board.toString(), "TB0000btSB0000bsLB0000blDB0000bdK00B00bKLB0000blSB0000bsTB0000bt#e2-e4#ftttt#0");
    }

    @Test
    public void movesWithoutAction() {
        board.applyMove(MoveFactory.getMove("b1-c3"));
        assertEquals(board.getMovesWithoutAction(), 1);
        board.applyMove(MoveFactory.getMove("b8-c6"));
        assertEquals(board.getMovesWithoutAction(), 2);


    }

    @Test
    public void castling() {
        board.applyMove(MoveFactory.getMove("b1-c3"));
        board.applyMove(MoveFactory.getMove("e7-e5"));
        board.applyMove(MoveFactory.getMove("a1-b1"));
        assertFalse(board.canWhiteQueenCastle());
        assertTrue(board.canWhiteKingCastle());
    }


}
