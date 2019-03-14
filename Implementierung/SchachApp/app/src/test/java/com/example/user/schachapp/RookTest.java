package com.example.user.schachapp;

import com.example.user.schachapp.chessLogic.BoardState;
import com.example.user.schachapp.chessLogic.Move;
import com.example.user.schachapp.chessLogic.Position;
import com.example.user.schachapp.chessLogic.Rook;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class RookTest {

    static Rook whiteRook;
    static Rook blackRook;
    static BoardState boardState;

    @BeforeClass
    public static void setRook() {
        whiteRook = new Rook(true);
        blackRook = new Rook(false);
        boardState = new BoardState("0T0bbs00" +
                "0B000000" +
                "0B00b00l" +
                "DLB0STb0" +
                "KB00T00d" +
                "L00B0sbt" +
                "SB00b00k" +
                "000B0lb0" +
                "XXtttttX0");
    }

    @Test
    public void getPossiblePositionsForWhitePawnA2 () {
        List<Move> positions = new ArrayList<Move>();
        Move moveFromA2ToA3 = new Move(new Position("a2"),new Position("a3"));
        Move moveFromA2ToA1 = new Move(new Position("a2"),new Position("a1"));
        Move moveFromA2ToA4 = new Move(new Position("a2"),new Position("a4"));

        positions.add(moveFromA2ToA1);
        positions.add(moveFromA2ToA3);
        positions.add(moveFromA2ToA4);

        List<Move> possibleMoves = whiteRook.getMovement(new Position("a2"),boardState);
        assertTrue(equalsList(possibleMoves,positions));
    }

    @Test
    public void getPossiblePositionsForWhitePawnE5 () {
        List<Move> positions = new ArrayList<Move>();
        Move moveFromE5ToE6 = new Move(new Position("e5"),new Position("e6"));
        Move moveFromE5ToE7 = new Move(new Position("e5"),new Position("e7"));
        Move moveFromE5ToE8 = new Move(new Position("e5"),new Position("e8"));
        Move moveFromE5ToE4 = new Move(new Position("e5"),new Position("e4"));
        Move moveFromE5ToE3 = new Move(new Position("e5"),new Position("e3"));
        Move moveFromE5ToF5 = new Move(new Position("e5"),new Position("f5"));
        Move moveFromE5ToG5 = new Move(new Position("e5"),new Position("g5"));

        positions.add(moveFromE5ToE3);
        positions.add(moveFromE5ToE4);
        positions.add(moveFromE5ToE6);
        positions.add(moveFromE5ToE7);
        positions.add(moveFromE5ToE8);
        positions.add(moveFromE5ToF5);
        positions.add(moveFromE5ToG5);
        List<Move> possibleMoves = whiteRook.getMovement(new Position("e5"),boardState);
        assertTrue(equalsList(possibleMoves,positions));
    }

    @Test
    public void getPossiblePositionsForBlackPawnF8 () {
        List<Move> possibleMoves = blackRook.getMovement(new Position("f8"),boardState);
        assertTrue(possibleMoves.isEmpty());
    }

    @Test
    public void getPossiblePositionsForBlackPawnD6 () {
        List<Move> possibleMoves = blackRook.getMovement(new Position("d6"),boardState);
        assertTrue(possibleMoves.toString().equals("[d6-c6, d6-b6, d6-e6, d6-d5]"));
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
