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

    public Game(User user1, User user2, BoardState board) {
        ruler = new ChessRuleProvider();
        this.board = board;
        whitePlayer = new Player(user1);
        blackPlayer = new Player(user2);
    }

    public BoardState getBoard() {
        return board;
    }

    public void setBoard(BoardState board) {
        this.board = board;
    }

    public Player getWhitePlayer() {
        return whitePlayer;
    }

    public Player getBlackPlayer() {
        return blackPlayer;
    }

    public boolean whiteToMove() {
        return board.whiteToMove();
    }

    public boolean hasPieceAt(Position position) {return board.hasPieceAt(position);}

    public Piece getPieceAt(Position position) {return board.getPieceAt(position);}

    public List<Move> getPossibleMoves(Position position) {
        return ruler.getLegalMoves(position, this.board);
    }

    public boolean isLegalMove(Move move) {
        return ruler.isLegalMove(move, this.board);
    }

    public void applyMove(String moveString) {
        Move move = new Move(null, null);
        board.applyMove(move);
    }

    public void applyMove(Move move) {
        board.applyMove(move);
    }

    public boolean hasEnded() {return ruler.hasEnded(this.board);}

    public Result getResult() {return ruler.getResult();}

    public String toString() {return null;}
}
