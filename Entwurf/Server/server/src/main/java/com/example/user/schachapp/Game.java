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

    public Result getResult() {
        return ruler.getResult();
    }
    public boolean hasEnded() {
        return ruler.hasEnded(board);
    }
    public List<Position> getPossiblePositions(String position) {
        return null;
    }
    public void applyMove (Move move) {
        board.applyMove(move);
    }
    public void applyMove(String move) {
        board.applyMove(new Move(move));
    }
    public void endGame(Result result) {

    }

}
