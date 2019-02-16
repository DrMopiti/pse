package com.example.user.schachapp.chessLogic;

import java.util.List;

/**
 * A interface which determines what methods a ruleProvider should implement
 */
public interface RuleProvider {
    /**
     * Should return a start state of a board for specific rules.
     * @return start state
     */
    BoardState getStartState();

    /**
     * Should return all legal moves for a piece at give position on given board
     * @param position the position of the piece
     * @param boardState the board to calculate on
     * @return a list of legal moves
     */
    List<Move> getLegalMoves(Position position, BoardState boardState);

    /**
     * Should check if a given move is legal on a given board.
     * @param move the move to be checked if legal
     * @param boardState the board to calculate on
     * @return true if legal, false if not
     */
    boolean isLegalMove(Move move, BoardState boardState);

    /**
     * Should check if given game has ended.
     * @param board the board to calculate on
     * @return true if it has ended, false if not
     */
    boolean hasEnded(BoardState board);

    /**
     * Should return the result of given game.
     * @param board the board to calculate on
     * @return the result of the game on given board
     */
    Result getResult(BoardState board);
}
