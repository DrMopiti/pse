package com.example.user.schachapp;

import com.example.user.schachapp.chessLogic.BoardState;
import com.example.user.schachapp.chessLogic.EnPassant;
import com.example.user.schachapp.chessLogic.Move;
import com.example.user.schachapp.chessLogic.Pawn;
import com.example.user.schachapp.chessLogic.Position;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PawnTest {

    static Pawn whitePawn;
    static Pawn blackPawn;
    static BoardState boardState;
    static BoardState boardState2;
    static BoardState boardEnPassant;

    @BeforeClass
    public static void setPawn(){
        blackPawn = new Pawn(false);
        whitePawn = new Pawn (true);
        boardState = new BoardState("T00B00bt" +
                "0B0s00b0" +
                "LBS00b0l" +
                "0D0B00bd" +
                "KL0B0b00" +
                "00Slbs0t" +
                "0B0000bk" +
                "TB0000b0" +
                "XXtttttX0");

        boardState2 = new BoardState("T00B00bt" +
                "0B0s00b0" +
                "LBS00b0l" +
                "0l00B0bd" +
                "KL0B0b00" +
                "00S0bs0t" +
                "0B0000bk" +
                "TB0000b0" +
                "XXtttttX0");

        boardEnPassant = new BoardState("TB0000bt" +
                "S000B0bs" +
                "LB00b00l" +
                "DB0000bd" +
                "KB000b0k" +
                "LB0000bl" +
                "SB0000bs" +
                "TB0000bt" +
                "XXtttttX0");

    }

    @Test
    public void getPossiblePositionsForWhitePawnA4(){
        List<Move> possibleMoves = whitePawn.getMovement(new Position("a4"), boardState);
        assertEquals("[a4-a5]",possibleMoves.toString());
    }

    @Test
    public void getPossiblePositionsForWhitePawnH2(){
        List<Move> possibleMoves = whitePawn.getMovement(new Position("h2"), boardState);
        assertEquals("[h2-h3, h2-h4]",possibleMoves.toString());
    }




    @Test
    public void getPossiblePositionsForWhitePawnD5() {
        BoardState spy = Mockito.spy(boardState2);
        Move lastMove = new Move(new Position("f4"), new Position("d2"));
        when(spy.getLastMove()).thenReturn(lastMove);
        List<Move> possibleMoves = whitePawn.getMovement(new Position("d5"), spy);
        assertEquals("[d5-d6, d5-c6, d5-e6]",possibleMoves.toString());
    }


    @Test
    public void getPossiblePositionsForWhitePawnE4(){
        List<Move> possibleMoves = whitePawn.getMovement(new Position("e4"),boardState);
        assertEquals("[e4-e5, e4-f5]",possibleMoves.toString());
    }

    @Test
    public void getPossiblePositionsForBlackPawnD7(){
        List<Move> possibleMoves = blackPawn.getMovement(new Position("d7"),boardState2);
        assertEquals("[d7-d6]",possibleMoves.toString());
    }

    @Test
    public void getPossiblePositionsForBlackPawnE6(){
        List<Move> possibleMoves = blackPawn.getMovement(new Position("e6"),boardState2);
        assertEquals("[e6-e5, e6-d5]",possibleMoves.toString());
    }


    @Test
    public void testEnPassantForWhitePawnB5(){
        BoardState spy = Mockito.spy(boardEnPassant);
        Move lastMove = new Move(new Position("c7"),new Position("c5"));
        when(spy.getLastMove()).thenReturn(lastMove);
        List<Move> possibleMoves = whitePawn.getMovement(new Position("b5"),spy);
        assertEquals("[b5-b6, b5-c6-E]",possibleMoves.toString());
    }


    @Test
    public void testEnPassantForBlackPawnB4() {
        BoardState boardForBlackEnPassant = new BoardState("T00B00bt" +
                "SB0b000s" +
                "L0B000bl" +
                "DB0000bd" +
                "K00B00bk" +
                "LB0000bl" +
                "SB0000bs" +
                "TB0000bt" +
                "XXtttttX0");

        BoardState spy = Mockito.spy(boardForBlackEnPassant);
        Move lastMove = new Move(new Position("a2"), new Position("a4"));
        when(spy.getLastMove()).thenReturn(lastMove);
        List<Move> possibleMoves = blackPawn.getMovement(new Position("b4"), spy);
        assertEquals("[b4-b3, b4-c3, b4-a3-E]",possibleMoves.toString());
    }



    @Test
    public void testEnPassantForWhitePawnA5WithNoMovementToLeft() {
        BoardState boardForWhiteEnPassant = new BoardState("T000Bb0t" +
                "SB00b00s" +
                "LB0000bl" +
                "DB0000bd" +
                "KB0000bk" +
                "LB0000bl" +
                "SB0000bs" +
                "TB0000bt" +
                "XXtttttX0");

        BoardState spy = Mockito.spy(boardForWhiteEnPassant);
        Move lastMove = new Move(new Position("b7"), new Position("b5"));
        when(spy.getLastMove()).thenReturn(lastMove);
        List<Move> possibleMoves = whitePawn.getMovement(new Position("a5"), spy);
        assertEquals("[a5-b6-E]",possibleMoves.toString());

    }
}