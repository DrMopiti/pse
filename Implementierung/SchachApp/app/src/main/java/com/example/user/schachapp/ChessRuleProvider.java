package com.example.user.schachapp;

import java.util.ArrayList;
import java.util.List;

public class ChessRuleProvider implements RuleProvider {

    @Override
    public BoardState getStartState() {
        return new BoardState("TB000000btSB000000bsLB000000blDB000000bdKB000000bKLB000000blSB000000bsTB000000bt##ttttt#0");
    }
    
    @Override
    public List<Move> getLegalMoves(Position position, BoardState board) {
        List<Move> legalMoves = new ArrayList<>();
        for (Move move: getPossibleMoves(position, board)) {
            if(isLegalMove(move, board)) {
               legalMoves.add(move);
            }
        }
        return legalMoves;
    }

    @Override
    public boolean isLegalMove(Move move, BoardState board) {
        if (move instanceof Castling) {
            BoardState test1 = board.clone();
            test1.applyMove(move);

            //King cannot jump over covered squares
            BoardState test2 = board.clone();
            int jumpOver = (move.getStart().getX() + move.getGoal().getX()) / 2;
            test2.applyMove(new Move(move.getStart(), new Position(jumpOver, move.getStart().getY())));

            return (!isChecked(board.whiteToMove(), test1) && !isChecked(board.whiteToMove(), test2));
        } else {
            BoardState test = board.clone();
            test.applyMove(move);
            return !isChecked(board.whiteToMove(), test);
        }
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
        if (board.hasPieceAt(position)) {
            return board.getPieceAt(position).getMovement(position, board);
        }
        return null;
    }

    private boolean isChecked(boolean color, BoardState board) {

        Position kingPos = board.getKingOfColor(color);
        List<Position> opponentsPieces = board.getPiecesOfColor(!color);

        for (Position p: opponentsPieces) {             //checks if any opposing Piece covers the Kings Position and returns true if so
            if (board.getPieceAt(p) instanceof Pawn) {

            }
            for (Move m: getPossibleMoves(p, board)) {
                if (!((board.getPieceAt(p) instanceof Pawn) && (m.getStart().getX() == m.getGoal().getX()))) { //Pawns cannot threat Positions in front of them
                    if(m.getGoal().equals(kingPos)) {
                        return true;
                    }
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
        return (isChecked(board.whiteToMove(), board) && !hasLegalMove(board));
    }

    private boolean isStaleMate(BoardState board) {
        return !hasLegalMove(board);
    }

    private boolean isDraw(BoardState board) {
        if (board.getMovesWithoutAction() >= 50) {
            return true;
        }
        return false;
    }
}
