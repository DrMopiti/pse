package com.example.user.schachapp;

import com.example.user.schachapp.chessLogic.BoardState;
import com.example.user.schachapp.chessLogic.ChessRuleProvider;
import com.example.user.schachapp.chessLogic.Move;
import com.example.user.schachapp.chessLogic.MoveFactory;
import com.example.user.schachapp.chessLogic.RuleProvider;

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
        assertEquals(board.toString(), "TB0000bt0B0000bsLBS000blDB0000bdKB0000bKLB0000blSB0000bsTB0000bt#b1-c3#ftttt#1");
    }

    @Test
    public void castling() {
        board.applyMove(MoveFactory.getMove("b1-c3"));
        board.applyMove(MoveFactory.getMove("e7-e5"));
        board.applyMove(MoveFactory.getMove("a1-b1"));
        assertEquals(board.toString(), "0B0000btTB0000bsLBS000blDB0000bdKB00b00KLB0000blSB0000bsTB0000bt#a1-b1#ftftt#1");
    }


}
