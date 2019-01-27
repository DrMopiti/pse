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
                "##ttttt#0");

        boardState2 = new BoardState("T00B00bt" +
                "0B0s00b0" +
                "LBS00b0l" +
                "0l00B0bd" +
                "KL0B0b00" +
                "00S0bs0t" +
                "0B0000bk" +
                "TB0000b0" +
                "##ttttt#0");

        boardEnPassant = new BoardState("TB0000bt" +
                "S000B0bs" +
                "LB00b00l" +
                "DB0000bd" +
                "KB000b0k" +
                "LB0000bl" +
                "SB0000bs" +
                "TB0000bt" +
                "##ttttt#0");

    }

    @Test
    public void getPossiblePositionsForWhitePawnA4(){
        List<Move> positions = new ArrayList<Move>();
        Move moveFromA4ToA5 = new Move(new Position("a4"),new Position("a5"));
        positions.add(moveFromA4ToA5);
        assertTrue(equalsList(whitePawn.getMovement(new Position("a4"),boardState),positions));
    }

    @Test
    public void getPossiblePositionsForWhitePawnH2(){
        List<Move> positions = new ArrayList<Move>();
        Move moveFromH2ToH3 = new Move(new Position("h2"),new Position("h3"));
        Move moveFromH2ToH4 = new Move(new Position("h2"),new Position("h4"));
        positions.add(moveFromH2ToH4);
        positions.add(moveFromH2ToH3);
        List<Move> possibleMoves = whitePawn.getMovement(new Position("h2"),boardState);
        assertTrue(equalsList(possibleMoves,positions));
    }




    @Test
    public void getPossiblePositionsForWhitePawnD5(){
        BoardState spy = Mockito.spy(boardState2);

        List<Move> positions = new ArrayList<Move>();
        Move moveFromD5ToE6 = new Move(new Position("d5"),new Position("e6"));
        Move moveFromD5ToD6 = new Move(new Position("d5"),new Position("d6"));
        Move moveFromD5ToC6 = new Move(new Position("d5"),new Position("c6"));
        positions.add(moveFromD5ToC6);
        positions.add(moveFromD5ToE6);
        positions.add(moveFromD5ToD6);
        Move lastMove = new Move(new Position("f4"),new Position("d2"));
        when(spy.getLastMove()).thenReturn(lastMove);
        List<Move> possibleMoves = whitePawn.getMovement(new Position("d5"),spy);
        assertTrue(equalsList(possibleMoves,positions));
    }


    @Test
    public void getPossiblePositionsForWhitePawnE4(){

        List<Move> positions = new ArrayList<Move>();
        Move moveFromE4ToE5 = new Move(new Position("e4"),new Position("e5"));
        Move moveFromE4ToF5 = new Move(new Position("e4"),new Position("f5"));
        positions.add(moveFromE4ToF5);
        positions.add(moveFromE4ToE5);
        List<Move> possibleMoves = whitePawn.getMovement(new Position("e4"),boardState);
        assertTrue(equalsList(possibleMoves,positions));
    }

    @Test
    public void getPossiblePositionsForBlackPawnD7(){
        List<Move> positions = new ArrayList<Move>();
        Move moveFromD7ToD6 = new Move(new Position("d7"),new Position("d6"));
        positions.add(moveFromD7ToD6);
        List<Move> possibleMoves = blackPawn.getMovement(new Position("d7"),boardState2);
        assertTrue(equalsList(possibleMoves,positions));
    }

    @Test
    public void getPossiblePositionsForBlackPawnE6(){
        List<Move> positions = new ArrayList<Move>();
        Move moveFromE6ToE5 = new Move(new Position("e6"),new Position("e5"));
        Move moveFromE6ToD5 = new Move(new Position("e6"),new Position("d5"));
        positions.add(moveFromE6ToD5);
        positions.add(moveFromE6ToE5);
        List<Move> possibleMoves = blackPawn.getMovement(new Position("e6"),boardState2);
        assertTrue(equalsList(possibleMoves,positions));
    }

    @Test
    public void testEnPassantForWhitePawnB5(){
        BoardState spy = Mockito.spy(boardEnPassant);
        List<Move> positions = new ArrayList<Move>();
        Move moveFromB5ToE6 = new Move(new Position("b5"),new Position("b6"));
        Move moveFromB5ToC6 = new EnPassant(new Position("b5"),new Position("c6"));
        positions.add(moveFromB5ToC6);
        positions.add(moveFromB5ToE6);
        Move lastMove = new Move(new Position("c7"),new Position("c5"));
        when(spy.getLastMove()).thenReturn(lastMove);
        List<Move> possibleMoves = whitePawn.getMovement(new Position("b5"),spy);
        assertTrue(equalsList(possibleMoves,positions));
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
                "##ttttt#0");

        BoardState spy = Mockito.spy(boardForBlackEnPassant);
        List<Move> positions = new ArrayList<Move>();
        Move moveFromB4ToB3 = new Move(new Position("b4"), new Position("b3"));
        Move moveFromB4ToC3 = new Move(new Position("b4"), new Position("c3"));
        Move moveFromB4ToA3 = new EnPassant(new Position("b4"), new Position("a3"));
        positions.add(moveFromB4ToA3);
        positions.add(moveFromB4ToB3);
        positions.add(moveFromB4ToC3);
        Move lastMove = new Move(new Position("a2"), new Position("a4"));
        when(spy.getLastMove()).thenReturn(lastMove);

        List<Move> possibleMoves = blackPawn.getMovement(new Position("b4"), spy);
        assertTrue(equalsList(possibleMoves, positions));

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
                "##ttttt#0");

        BoardState spy = Mockito.spy(boardForWhiteEnPassant);
        List<Move> positions = new ArrayList<Move>();
        Move moveFromA5ToB6 = new EnPassant(new Position("a5"), new Position("b6"));
        positions.add(moveFromA5ToB6);
        Move lastMove = new Move(new Position("b7"), new Position("b5"));
        when(spy.getLastMove()).thenReturn(lastMove);
        List<Move> possibleMoves = whitePawn.getMovement(new Position("a5"), spy);
        assertTrue(equalsList(possibleMoves, positions));

    }



    private boolean equalsList (List<Move> list1, List<Move> list2){
        if(list1.size() != list2.size()) {
            return false;
        }
       loop: for (Move move: list1) {
            for (int i = 0; i < list2.size(); i++) {
                if ((i == list2.size() - 1) && !move.equals(list2.get(i))){
                    return false;
                }
                if (move.equals(list2.get(i))){
                    continue loop;
                }
            }
        }
        return true;
    }


}