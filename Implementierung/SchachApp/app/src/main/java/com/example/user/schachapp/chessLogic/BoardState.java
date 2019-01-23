package com.example.user.schachapp.chessLogic;

import java.util.ArrayList;
import java.util.List;

public class BoardState {
    private Tile[][] tiles;
    private Move lastMove;
    private boolean whiteToMove;
    private boolean whiteKingCastle;
    private boolean whiteQueenCastle;
    private boolean blackKingCastle;
    private boolean blackQueenCastle;
    private int movesWithoutAction;

    public BoardState() {
        tiles = new Tile[8][8];
        lastMove = null;
        whiteKingCastle = true;
        whiteQueenCastle = true;
        blackKingCastle = true;
        blackQueenCastle = true;
        whiteToMove = true;
        movesWithoutAction = 0;
    }

    public BoardState(String string) {
        //splits the String in 4 Sectors: Pieces, Last Move, Booleans, Moves without Action
        String[] sectors = string.split("#");
        if (sectors.length != 4) {
            throw new IllegalArgumentException("String in wrong format");
        }

        //sets all Pieces and empty Positions
        String[] pieces = sectors[0].split("");
        if (pieces.length != 64) {
            throw new IllegalArgumentException("String for pieces not 64 chars long");
        }
        tiles = new Tile[8][8];
        for (int i = 0; i <= 7; i++) {
            for (int h = 0; h <= 7; h++) {
                tiles[i][h] = new Tile(PieceFactory.getPiece(pieces[i*8+h]));
            }
        }

        //sets lastMove
        if(sectors[1].equals("")) {
            lastMove = null;
        } else {
            lastMove = MoveFactory.getMove(sectors[1]);
            if (lastMove == null) {
                throw new IllegalArgumentException("String for last move not correct");
            }
        }

        //sets all Booleans
        String[] bools = sectors[2].split("");
        if (bools.length != 5) {
            throw new IllegalArgumentException("String for booleans not correct");
        }
        whiteToMove = bools[0].equals("t");
        whiteKingCastle = bools[1].equals("t");
        whiteQueenCastle = bools[2].equals("t");
        blackKingCastle = bools[3].equals("t");
        blackQueenCastle = bools[4].equals("t");

        //sets movesWithoutAction
        try {
            movesWithoutAction = Integer.parseInt(sectors[3]);
        } catch (IllegalArgumentException e) {
            movesWithoutAction = 0;
        }
    }

    public void applyMove(Move move) {
        int startX = move.getStart().getX();
        int startY = move.getStart().getY();
        int goalX = move.getGoal().getX();
        int goalY = move.getGoal().getY();

        //update attributes
        lastMove = move;
        movesWithoutAction++;
        if (getPieceAt(move.start) instanceof Pawn || hasPieceAt(move.goal)) {
            movesWithoutAction = 0;
        }
        if (whiteKingCastle || whiteQueenCastle || blackKingCastle ||blackQueenCastle) {
            if (getPieceAt(move.start) instanceof King) {
                if (getPieceAt(move.start).isWhite) {
                    whiteKingCastle = false;
                    whiteQueenCastle = false;
                } else {
                    blackKingCastle = false;
                    blackQueenCastle = false;
                }
            }
            if (getPieceAt(move.start) instanceof Rook) {
                if (getPieceAt(move.start).isWhite) {
                    if (move.getStart().equals(new Position("a8"))) {
                        whiteKingCastle = false;
                    }
                    if (move.getStart().equals(new Position("a1"))) {
                        whiteQueenCastle = false;
                    }
                } else {
                    if (move.getStart().equals(new Position("h8"))) {
                        blackKingCastle = false;
                    }
                    if (move.getStart().equals(new Position("h1"))) {
                        blackQueenCastle = false;
                    }
                }
            }
        }

        //execute Move
        tiles[goalX][goalY].setPiece(getPieceAt(move.getStart()));
        tiles[startX][startY].removePiece();

        //execute additional things for special Moves
        if(move instanceof EnPassant) {
            int pawnX = ((EnPassant) move).getRemovePawn().getX();
            int pawnY = ((EnPassant) move).getRemovePawn().getY();
            tiles[pawnX][pawnY].removePiece();
        } else if (move instanceof Castling) {
            Move rookMove = ((Castling) move).getRookMove();
            int rookStartX = rookMove.getStart().getX();
            int rookStartY = rookMove.getStart().getY();
            int rookGoalX = rookMove.getGoal().getX();
            int rookGoalY = rookMove.getGoal().getY();
            tiles[rookGoalX][rookGoalY].setPiece(getPieceAt(rookMove.getStart()));
            tiles[rookStartX][rookStartY].removePiece();
        } else if (move instanceof Promotion) {
            tiles[goalX][goalY].setPiece(((Promotion) move).getPromotion());
        }
    }

    public boolean hasPieceAt(Position position) {
        return tiles[position.getX()][position.getY()].getPiece() != null;
    }

    public Piece getPieceAt(Position position) {
        return tiles[position.getX()][position.getY()].getPiece();
    }

    public List<Position> getPiecesOfColor(boolean white) {
        List<Position> pieces = new ArrayList<>();
        for (int i = 0; i <= 7; i++) {
            for (int h = 0; h <= 7; h++) {
                if(tiles[i][h].getPiece().isWhite == white) {
                    pieces.add(new Position(i, h));
                }
            }
        }
        return pieces;
    }

    public Position getKingOfColor(boolean white) {
        for (int i = 0; i <= 7; i++) {
            for (int h = 0; h <= 7; h++) {
                if(tiles[i][h].getPiece() instanceof King && (tiles[i][h].getPiece().isWhite == white)) {
                    return new Position(i, h);
                }
            }
        }
        return null;
    }
    public Move getLastMove() {
        return lastMove;
    }

    public boolean whiteToMove() {
        return whiteToMove;
    }

    boolean canWhiteKingCastle() {
        return whiteKingCastle;
    }

    public boolean canWhiteQueenCastle() {
        return whiteQueenCastle;
    }

    public boolean canBlackKingCastle() {
        return blackKingCastle;
    }

    public boolean canBlackQueenCastle() {
        return blackQueenCastle;
    }

    public int getMovesWithoutAction() {
        return movesWithoutAction;
    }

    public String toString() {

        //converts the board to String
        String pieces = "";
        for (int i = 0; i <= 7; i++) {
            for (int h = 0; h <= 7; h++) {
                if (tiles[i][h].equals(null)) {
                   pieces = pieces + 0;
                } else {
                   pieces = pieces + tiles[i][h].getPiece().toString();
                }
            }
        }

        //converts lastMove
        String move = "";
        if(lastMove != null) {
            move = lastMove.toString();
        }

        //converts the booleans
        String bools = "";
        bools = bools + (whiteToMove ? "t" : "f");
        bools = bools + (whiteKingCastle ? "t" : "f");
        bools = bools + (whiteQueenCastle ? "t" : "f");
        bools = bools + (blackKingCastle ? "t" : "f");
        bools = bools + (blackQueenCastle ? "t" : "f");

        //converts movesWithoutAction
        String noAction = String.valueOf(movesWithoutAction);

        return pieces + "#" + move + "#" + bools + "#" + noAction;
    }

    public BoardState clone() {
        return new BoardState(this.toString());
    }
}
