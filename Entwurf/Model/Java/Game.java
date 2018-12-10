package com.example.user.schachapp;

public class Game {
    RuleProvider ruler;
    BoardState board;
    Player whitePlayer;
    Player blackPlayer;
    public Game(User user1, User user2) {
        ruler = new ChessRuleProvider();
        board = new ChessBoardState();
        whitePlayer = new Player(user1);
        blackPlayer = new Player(user2);
    }
}
