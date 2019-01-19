package com.example.user.schachapp;

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

        //sets all Pieces and empty Positions
        String[] pieces = sectors[0].split("");
        tiles = new Tile[8][8];
        for (int i = 0; i <= 7; i++) {
            for (int h = 0; h <= 7; h++) {
                tiles[i][h].setPiece(pieceFactory(pieces[i*8+h]));
            }
        }

        //sets lastMove
        if(sectors[1].equals("")) {
            lastMove = null;
        } else {
            String[] move = sectors[1].split("-");
            Position start = new Position(move[0]);
            Position goal = new Position(move[1]);
            switch (move.length)  {
                case 2:
                    lastMove = new Move(start, goal);
                    break;
                case 3:
                    if (move[2].length() == 1) {
                        lastMove = new Promotion(start, goal, pieceFactory(move[2]));
                    }
                    if (move[2].length() == 2) {
                        lastMove = new EnPassant(start, goal);
                    }
                    break;
                case 4:
                    lastMove = new Castling(start, goal);
                    break;
                default:
                    lastMove = null;
            }
        }

        //sets all Booleans
        String[] bools = sectors[2].split("");
        whiteToMove = bools[0].equals("t");
        whiteKingCastle = bools[1].equals("t");
        whiteQueenCastle = bools[2].equals("t");
        blackKingCastle = bools[3].equals("t");
        blackQueenCastle = bools[4].equals("t");

        //sets movesWithoutAction
        movesWithoutAction = Integer.parseInt(sectors[3]);

    }

    private Piece pieceFactory(String pieceRepresentation) {
        Piece p;
        switch (pieceRepresentation) {
            case "K":
                p = (new King(true));
                break;
            case "k":
                p = (new King(false));
                break;
            case "D":
                p = (new Queen(true));
                break;
            case "d":
                p = (new Queen(false));
                break;
            case "L":
                p = (new Bishop(true));
                break;
            case "l":
                p = (new Bishop(false));
                break;
            case "S":
                p = (new Knight(true));
                break;
            case "s":
                p = (new Knight(false));
                break;
            case "B":
                p = (new Pawn(true));
                break;
            case "b":
                p = (new Pawn(false));
                break;
            default:
                p = null;
        }
        return p;
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
                    if (move.getGoal().equals(new Position("a8"))) {
                        whiteKingCastle = false;
                    }
                    if (move.getGoal().equals(new Position("a1"))) {
                        whiteQueenCastle = false;
                    }
                } else {
                    if (move.getGoal().equals(new Position("h8"))) {
                        blackKingCastle = false;
                    }
                    if (move.getGoal().equals(new Position("h1"))) {
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
            applyMove(((Castling) move).getRookMove());
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
                   pieces = pieces + tiles[i][h].toString();
                }
            }
        }

        //converts lastMove
        String move = "";
        if(!lastMove.equals(null)) {
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
