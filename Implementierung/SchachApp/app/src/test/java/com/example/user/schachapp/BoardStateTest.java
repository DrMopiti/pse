package com.example.user.schachapp;

import com.example.user.schachapp.chessLogic.BoardState;
import com.example.user.schachapp.chessLogic.MoveFactory;
import com.example.user.schachapp.chessLogic.Pawn;
import com.example.user.schachapp.chessLogic.Position;

import org.junit.*;

import static org.junit.Assert.*;

public class BoardStateTest {
    public static BoardState board;

    @Before
    public void setBoard() {
        board = new BoardState("TB0000btSB0000bsLB0000blDB0000bdKB0000bKLB0000blSB0000bsTB0000bt##ttttt#0");
    }

    @Test
    public void stringTest() {
        assertEquals(board.toString(), "TB0000btSB0000bsLB0000blDB0000bdKB0000bKLB0000blSB0000bsTB0000bt##ttttt#0");
    }

    @Test
    public void applyMove() {
        board.applyMove(MoveFactory.getMove("e2-e4"));
        assertTrue(board.getPieceAt(new Position("e4")) instanceof Pawn);
        assertFalse(board.hasPieceAt(new Position("e2")));
    }

    @Test
    public void movesWithoutActionCounting() {
        board.applyMove(MoveFactory.getMove("b1-c3"));
        board.applyMove(MoveFactory.getMove("b8-c6"));
        assertEquals(board.getMovesWithoutAction(), 2);
    }

    @Test
    public void moveWithoutActionResetting() {
        board.applyMove(MoveFactory.getMove("b1-c3"));
        board.applyMove(MoveFactory.getMove("b8-c6"));
        board.applyMove(MoveFactory.getMove("e2-e4"));
        assertEquals(board.getMovesWithoutAction(), 0);
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
