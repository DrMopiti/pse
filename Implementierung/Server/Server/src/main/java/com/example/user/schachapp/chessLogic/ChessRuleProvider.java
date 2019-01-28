package com.example.user.schachapp.chessLogic;

import java.util.ArrayList;
import java.util.List;

public class ChessRuleProvider implements RuleProvider {

    @Override
    public BoardState getStartState() {
        return new BoardState("TB0000btSB0000bsLB0000blDB0000bdKB0000bKLB0000blSB0000bsTB0000bt##ttttt#0");
    }
    
    @Override
    public List<Move> getLegalMoves(Position position, BoardState board) {
        List<Move> legalMoves = new ArrayList<>();
        List<Move> possibleMoves = getPossibleMoves(position, board);
        for (Move move: possibleMoves) {
            if(isAllowedMove(move, board)) {
               legalMoves.add(move);
            }
        }
        return legalMoves;
    }

    @Override
    public boolean isLegalMove(Move move, BoardState board) { //checks if move is legal on this board
        Piece movingPiece;
        if (!board.hasPieceAt(move.getStart())) {
            System.out.println("No piece found at starting position");
            return false;
        }
        movingPiece = board.getPieceAt(move.getStart());
        if (movingPiece.isWhite != board.whiteToMove()) {
            System.out.println("Wrong color to move");
            return false;
        }
        List<Move> legalMoves = getLegalMoves(move.getStart(), board);
        for (Move m: legalMoves) {
            if (m.equals(move)) {
                return true;
            }
        }
        System.out.println("Not an allowed Move");
        return false;
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

    private boolean isAllowedMove(Move move, BoardState board) { //checks if moving player would be checked after move
        if (move instanceof Castling) {
            BoardState test1 = board.clone();
            test1.applyMove(move);

            //King cannot jump over covered squares
            BoardState test2 = board.clone();
            int jumpOver = (move.getStart().getX() + move.getGoal().getX()) / 2;
            test2.applyMove(new Move(move.getStart(), new Position(jumpOver, move.getStart().getY())));

            return (!isChecked(board.whiteToMove(), test1) && !isChecked(board.whiteToMove(), test2));
        }
        BoardState test = board.clone();
        test.applyMove(move);
        return !isChecked(board.whiteToMove(), test);
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
            List<Move> checkingMoves = getPossibleMoves(p, board);

            if (board.getPieceAt(p) instanceof Pawn) {      //Pawns cannot threat Positions in front of them
                for (Move m: checkingMoves) {
                    if ((m.getStart().getX() != m.getGoal().getX()) && (m.getGoal().equals(kingPos)))  {
                        return true;
                    }
                }
            } else {
                for (Move m : checkingMoves) {
                    if (m.getGoal().equals(kingPos)) {
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
