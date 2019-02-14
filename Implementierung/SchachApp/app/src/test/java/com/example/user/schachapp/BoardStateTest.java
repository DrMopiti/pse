package com.example.user.schachapp;

import com.example.user.schachapp.chessLogic.BoardState;
import com.example.user.schachapp.chessLogic.Move;
import com.example.user.schachapp.chessLogic.MoveFactory;
import com.example.user.schachapp.chessLogic.Pawn;
import com.example.user.schachapp.chessLogic.Position;

import org.junit.*;

import static org.junit.Assert.*;

public class BoardStateTest {
    public static BoardState board;

    @Before
    public void setBoard() {
        board = new BoardState("TB0000bt" +
                "SB0000bs" +
                "LB0000bl" +
                "DB0000bd" +
                "KB0000bk" +
                "LB0000bl" +
                "SB0000bs" +
                "TB0000bt" +
                "##ttttt#0");
    }

    @Test
    public void stringTest() {
        assertEquals(board.toString(), "TB0000btSB0000bsLB0000blDB0000bdKB0000bkLB0000blSB0000bsTB0000bt##ttttt#0");
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

    @Test(expected = IllegalArgumentException.class)
    public void boardParamaterNotCorrect() {
        BoardState board = new BoardState("");
    }

    /*@Test(expected = IllegalArgumentException.class)
    public void stringIsNotLegalForBoard() {
        BoardState board = new BoardState("TB0000btSB0000bsLB000LblDB0000bdKB0000bkLB00K0blSB00B0bsTB0000bt##ttttt#0");
    }
    */

    @Test
    public void lastMoveTest() {
        BoardState testBoard = new BoardState("TB0000bt" +
                "0B0000bs" +
                "LBS0b00l" +
                "DB0000bd" +
                "K00B00bk" +
                "LB0000bl" +
                "SB0000bs" +
                "TB0000bt" +
                "#b1-c3#ttttt#0");
        assertTrue(new Move(new Position("b1"),new Position("c3")).equals(testBoard.getLastMove()));
    }

    @Test (expected = IllegalArgumentException.class)
    public void notCorrectLastMove() {
        BoardState testBoard = new BoardState("TB0000bt" +
                "0B0000bs" +
                "LBS0b00l" +
                "DB0000bd" +
                "K00B00bk" +
                "LB0000bl" +
                "SB0000bs" +
                "TB0000bt" +
                "#b1-c2-m#ttttt#0");
    }

    @Test(expected = IllegalArgumentException.class)
    public void notCorrectBooleans() {
        BoardState testBoard = new BoardState("TB0000bt" +
                "0B0000bs" +
                "LBS0b00l" +
                "DB0000bd" +
                "K00B00bk" +
                "LB0000bl" +
                "SB0000bs" +
                "TB0000bt" +
                "##ftstt#0");
    }

    @Test()

    public void whitePiecesOnTheBoard() {
        BoardState testBoard = new BoardState("0B000000" +
                "000s0000" +
                "0B000000" +
                "00000000" +
                "K0000000" +
                "000Bt000" +
                "S0s0000k" +
                "00000000" +
                "##fffff#0");
        assertEquals("[a2, c2, e1, f4, g1]",testBoard.getPiecesOfColor(true).toString());
    }

    @Test
    public void blackPiecesOnTheBoard() {
        BoardState testBoard = new BoardState("0B000000" +
                "000s0000" +
                "0B000000" +
                "00000000" +
                "K0000000" +
                "000Bt000" +
                "S0s0000k" +
                "00000000" +
                "##fffff#0");
        assertEquals("[b4, f5, g3, g8]",testBoard.getPiecesOfColor(false).toString());
    }

    @Test
    public void positionOfWhiteKingOnTheBoard() {
        assertTrue(new Position("e1").equals(board.getKingOfColor(true)));
    }


    @Test
    public void positionOfBlackKingOnTheBoard() {
        assertTrue(new Position("e8").equals(board.getKingOfColor(false)));
    }

    @Test
    public void noBlackKingOnTheBoard() {
        BoardState testBoard = new BoardState("0B000000" +
                "000s0000" +
                "0B000000" +
                "00000000" +
                "K0000000" +
                "000Bt000" +
                "S0s00000" +
                "00000000" +
                "##fffff#0");
        assertNull(testBoard.getKingOfColor(false));
    }

}
