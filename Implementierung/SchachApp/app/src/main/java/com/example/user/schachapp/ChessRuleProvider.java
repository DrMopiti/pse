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
        List<Move> legalMoves = new ArrayList<>();
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

    private boolean isChecked(boolean color, BoardState board) {

        Position kingPos = board.getKingOfColor(color);
        List<Position> opponentsPieces = board.getPiecesOfColor(!color);

        for (Position p: opponentsPieces) {             //checks if any opposing Piece covers the Kings Position and returns true if so
            for(Move m: getPossibleMoves(p, board)) {
                if(m.getGoal().equals(kingPos)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean hasLegalMove(BoardState board) {
        List<Position> ownPieces = board.getPiecesOfColor(board.whiteToMove());

        for (Position p: ownPieces) {
            if (!getLegalMoves(p, board).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private boolean isMate(BoardState board) {
        return (isChecked(board.whiteToMove(), board) && hasLegalMove(board));
    }

    private boolean isStaleMate(BoardState board) {
        return hasLegalMove(board);
    }

    private boolean isDraw(BoardState board) {
        if (board.getMovesWithoutAction() >= 50) {
            return true;
        }
        return false;
    }
}
