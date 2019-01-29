package com.example.user.schachapp;

import com.example.user.schachapp.chessLogic.Castling;
import com.example.user.schachapp.chessLogic.EnPassant;
import com.example.user.schachapp.chessLogic.Move;
import com.example.user.schachapp.chessLogic.MoveFactory;
import com.example.user.schachapp.chessLogic.Position;
import com.example.user.schachapp.chessLogic.Promotion;
import com.example.user.schachapp.chessLogic.Queen;

import org.junit.*;

import static org.junit.Assert.*;

public class MoveFactoryTest {
    public static Move move;

    @Test
    public void movePositions() {
        move = MoveFactory.getMove("b3-g5");
        assertTrue(move.getStart().equals(new Position(1, 2)));
        assertTrue(move.getGoal().equals(new Position(6, 4)));
    }

    @Test
    public void castling() {
        move = MoveFactory.getMove("e1-g1-C");
        assertTrue(move instanceof Castling);
        assertTrue(((Castling) move).getRookMove().equals(MoveFactory.getMove("h1-f1")));
    }

    @Test
    public void promotion() {
        move = MoveFactory.getMove("e7-e8-D");
        assertTrue(move instanceof Promotion);
        assertTrue(((Promotion) move).getPromotion() instanceof Queen);
    }

    @Test
    public void enPassant() {
        move = MoveFactory.getMove("e5-d6-E");
        assertTrue(move instanceof EnPassant);
        assertTrue(((EnPassant) move).getRemovePawn().equals(new Position("d5")));
    }

    @Test
    public void wrongInput() {
        move = MoveFactory.getMove("wrong");
        assertTrue(move == null);
    }

    @Test
    public void samePosition() {
        move = MoveFactory.getMove("a1-a1");
        assertTrue(move == null);
    }

    @Test
    public void kingPromotion() {
        move = MoveFactory.getMove("a7-a8-K");
        assertTrue(move == null);
    }

    @Test
    public void tooManyPositions() {
        move = MoveFactory.getMove("a1-a2-a3");
        assertTrue(move == null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void incorrectPositions() {
        move = MoveFactory.getMove("k1-l9");
    }
}
