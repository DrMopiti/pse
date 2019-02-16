package com.example.user.schachapp.chessLogic;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements all chess rules, using several methods.
 */
public class ChessRuleProvider implements RuleProvider {

    /**
     * Returns a boardState at the starting position of a chess game, using the correct string.
     * @return a board at the beginning of a game
     */
    @Override
    public BoardState getStartState() {
        return new BoardState("TB0000bt" +
                "SB0000bs" +
                "LB0000bl" +
                "DB0000bd" +
                "KB0000bk" +
                "LB0000bl" +
                "SB0000bs" +
                "TB0000bt" +
                "##ttttt#0");
    }

    /**
     * Returns a list of legal moves for a piece on given position at given board.
     * Uses the getPossibleMoves and the isAllowedMove method to determine if a piece is pinned.
     * @param position the position of the piece
     * @param board the board on which the piece stands
     * @return a list of legal moves
     */
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

    /**
     * Checks if a given move is legal on a given board.
     * Checks if the piece is on the correct position, if the correct player is to move,
     * and then calculates the legal moves for the piece at given position and checks if they contain the given move
     * @param move the move to be checked if legal
     * @param board the board to calculate on
     * @return true if legal, false if not
     */
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

    /**
     * Checks if the game on the given board is ended by using isMate, isStaleMate and isDraw
     * @param board the board to calculate on
     * @return true if it has ended, false if not
     */
    @Override
    public boolean hasEnded(BoardState board) {
        return (isMate(board) || isStaleMate(board) || isDraw(board));
    }

    /**
     * Returns the result of the game on the given board. If the game has not ended, it returns null.
     * @param board the board to calculate on
     * @return the result of the game on given board, null if game has not ended
     */
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

    /**
     * Checks if the moving player would be checked after his move, making the move illegal.
     * @param move the move to be checked
     * @param board the board to calculate on
     * @return true if the player would not be checked, false if he would
     */
    private boolean isAllowedMove(Move move, BoardState board) {
        if (move instanceof Castling) {
            BoardState test1 = board.clone();
            test1.applyMove(move);

            //King cannot jump over covered squares
            BoardState test2 = board.clone();
            int jumpOver = (move.getStart().getX() + move.getGoal().getX()) / 2;
            test2.applyMove(new Move(move.getStart(), new Position(jumpOver, move.getStart().getY())));

            return (!isChecked(board.whiteToMove(), board) && !isChecked(board.whiteToMove(), test1) && !isChecked(board.whiteToMove(), test2));
        }
        BoardState test = board.clone();
        test.applyMove(move);
        return !isChecked(board.whiteToMove(), test);
    }

    /**
     * Returns a list of possible moves (not considering if piece is pinned) for the piece at given position on given board.
     * Uses the getMovement method of the piece at given position
     * @param position the pieces position
     * @param board the board to calculate on
     * @return list of posiible moves
     */
    private List<Move> getPossibleMoves(Position position, BoardState board) {
        if (board.hasPieceAt(position)) {
            return board.getPieceAt(position).getMovement(position, board);
        }
        return null;
    }

    /**
     * Checks if a player of given color is checked on the given board.
     * Does not care which player has to move next.
     * @param color the color of the player who can be checked, white = true, black = false
     * @param board the board to calculate on
     * @return true if player is checked, false if not
     */
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

    /**
     * Checks if a given board has legal moves for the player who has to move.
     * Checks for all pieces of this player if they have a legal move and returns true if so.
     * @param board the board to calculate on
     * @return true if the moving player has at least one legal move, false if not
     */
    private boolean hasLegalMove(BoardState board) {
        List<Position> ownPieces = board.getPiecesOfColor(board.whiteToMove());

        for (Position p: ownPieces) {
            if (!getLegalMoves(p, board).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the moving player is mated by checking if he is checked and has no legal moves.
     * @param board the board to calculate on
     * @return true if moving player is mated, false if not
     */
    private boolean isMate(BoardState board) {
        return (isChecked(board.whiteToMove(), board) && !hasLegalMove(board));
    }

    /**
     * Checks if the moving player is stale mated by checking is he has no legal moves left.
     * Does not check if the player is checked, so this method should be used after chekcing for mate.
     * @param board the board to calculate on
     * @return true if the player is stale mated, false if not
     */
    private boolean isStaleMate(BoardState board) {
        return !hasLegalMove(board);
    }

    /**
     * Checks if the given game is drawn, by checking if movesWithoutAction is >= 50 or not.
     * @param board the board to calculate on
     * @return true if it is a draw, false if not
     */
    private boolean isDraw(BoardState board) {
        if (board.getMovesWithoutAction() >= 50) {
            return true;
        }
        return false;
    }
}
