package com.example.user.schachapp;

import java.util.List;

public class Game {
    RuleProvider ruler;
    BoardState board;
    Player whitePlayer;
    Player blackPlayer;

    public Game(User user1, User user2) {
        ruler = new ChessRuleProvider();
        board = ruler.getStartState();
        whitePlayer = new Player(user1);
        blackPlayer = new Player(user2);
    }

    public Game(User user1, User user2, BoardState board) {}

    public BoardState getBoard() {
        return board;
    }

    public void setBoard(BoardState board) {}

    public Player getWhitePlayer() {
        return whitePlayer;
    }

    public Player getBlackPlayer() {
        return blackPlayer;
    }

    public boolean hasPieceAt(Position position, boolean isWhite) {return false;}

    public Piece getPieceAt(Position position) {return null;}

    public List<Position> getPossiblePositions(String position) {
        return null;
    }

    public void applyMove(String move) {}

    public void applyMove(Move move) {}

    public boolean hasEnded() {return false;}

    public Result getResult() {return null;}

    public String toString() {return null;}
}
