package com.example.user.schachapp;

import java.util.ArrayList;
import java.util.List;

public class ChessRuleProvider implements RuleProvider {

    @Override
    public BoardState getStartState() {
        return new BoardState();
    }
    
    @Override
    public List<Move> getLegalMoves(Position position, BoardState board) {
        List<Move> legalMoves = new ArrayList<Move>();
        List<Move> possibleMoves = getPossibleMoves(position, board);
        for (Move move: possibleMoves) {
            if(isLegalMove(move, board)) {
               legalMoves.add(move);
            }
        }
        return possibleMoves;
    }

    @Override
    public boolean isLegalMove(Move move, BoardState board) {
        BoardState test = board.clone();
        test.applyMove(move);
        return !isChecked(board.whiteToMove(), test);
    }

    @Override
    public boolean hasEnded(BoardState board) {
        return (isMate(board) || isStaleMate(board) || isDraw(board));
    }

    @Override
    public Result getResult(BoardState board) {
        if(isMate(board)) {
            if(board.whiteToMove()) {
                return new Result("0:1", "Matt");
            } else {
                return new Result("1:0", "Matt");
            }
        }
        if (isStaleMate(board)) {
            return new Result("0,5:0,5", "Patt");

        }
        if (isDraw(board)) {
            return new Result("0,5:0,5", "Unentschieden");
        }
        return null;
    }

    private List<Move> getPossibleMoves(Position position, BoardState board) {
        return board.getPieceAt(position).getMovement(position, board);
    }

    private boolean isChecked(boolean white, BoardState state) {
        return true;
    }
    private boolean isMate(BoardState state) {
        return false;
    }
    private boolean isStaleMate(BoardState state) {
        return false;
    }
    private boolean isDraw(BoardState state) {
        return false;
    }
}
