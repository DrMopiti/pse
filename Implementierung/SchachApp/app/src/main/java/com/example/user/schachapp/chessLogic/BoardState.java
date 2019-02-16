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

    public BoardState(String string) {
        //splits the String in 4 Sectors: Pieces, Last Move, Booleans, Moves without Action
        String[] sectors = string.split("#");
        if (sectors.length != 4 && !sectors[0].matches("[TSLDKBtsldkb0]{64}")) {
            throw new IllegalArgumentException("String in wrong format");
        }

      /*  if (!validityOfPiecesOnBoard(sectors[0])) {
            throw new IllegalArgumentException("String for pieces not 64 long or board is not correct");
        }
        */

        //sets all Pieces and empty Positions
        String[] pieces = sectors[0].split("");

        if (pieces[0].equals("")) {
            for (int i = 0; i < pieces.length - 1; i++) {
                pieces[i] = pieces[i+1];
            }
        }

        tiles = new Tile[8][8];
        for (int i = 0; i <= 7; i++) {
            for (int h = 0; h <= 7; h++) {
                tiles[i][h] = new Tile(PieceFactory.getPiece(pieces[i * 8 + h]));
            }
        }

        //sets lastMove
        if (sectors[1].equals("")) {
            lastMove = null;
        } else {
            lastMove = MoveFactory.getMove(sectors[1]);
            if (lastMove == null) {
                throw new IllegalArgumentException("String for last move not correct");
            }
        }

        //sets all Booleans
        String[] bools = sectors[2].split("");
        if (bools[0].equals("")) {
            for (int i = 0; i < bools.length - 1; i++) {
                bools[i] = bools[i+1];
            }
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
        whiteToMove = !whiteToMove;
        if (getPieceAt(move.start) instanceof Pawn || hasPieceAt(move.goal)) {
            movesWithoutAction = 0;
        }
        if (whiteKingCastle || whiteQueenCastle || blackKingCastle || blackQueenCastle) {
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
        if (move instanceof EnPassant) {
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
        return (tiles[position.getX()][position.getY()].getPiece() != null);
    }

    public Piece getPieceAt(Position position) {
        return tiles[position.getX()][position.getY()].getPiece(); //getPiece already returns null if tile is empty
    }

    public List<Position> getPiecesOfColor(boolean white) {
        List<Position> pieces = new ArrayList<>();
        Position temp;
        for (int i = 0; i <= 7; i++) {
            for (int h = 0; h <= 7; h++) {
                temp = new Position(i, h);
                if (hasPieceAt(temp) && tiles[i][h].getPiece().isWhite == white) {
                    pieces.add(temp);
                }
            }
        }
        return pieces;
    }

    public Position getKingOfColor(boolean white) {
        for (int i = 0; i <= 7; i++) {
            for (int h = 0; h <= 7; h++) {
                if (tiles[i][h].getPiece() instanceof King && (tiles[i][h].getPiece().isWhite == white)) {
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

    public boolean canWhiteKingCastle() {
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
                if (tiles[i][h].getPiece() == null) {
                    pieces = pieces + "0";           //placeholder for empty tiles
                } else {
                    pieces = pieces + tiles[i][h].getPiece().toString();
                }
            }
        }

        //converts lastMove
        String move = "";
        if (lastMove != null) {
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

   /* private boolean validityOfPiecesOnBoard(String pieces) {

        if (!pieces.matches("[TSLDKBtsldkb0]{64}")) {
           return false;
        }

        int counterForWhiteKing = 0;
        int counterForBlackKing = 0;
        int counterForWhiteBishop = 0;
        int counterForBlackBishop = 0;
        int counterForWhiteRook = 0;
        int counterForBlackRook = 0;
        int counterForWhitePawn = 0;
        int counterForBlackPawn = 0;
        int counterForWhiteKnight = 0;
        int counterForBlackKnight = 0;
        int counterForWhiteQueen = 0;
        int counterForBlackQueen = 0;
        int counterForEmpty = 0;

        for (int i = 0; i < pieces.length(); i++) {
            switch (pieces.charAt(i)) {
                case 'K':
                    ++counterForWhiteKing;
                      break;
                case 'k':
                    ++counterForBlackKing;
                    break;
                case 'D':
                    ++counterForWhiteQueen;
                    break;
                case 'd':
                    ++counterForBlackQueen;
                    break;
                case 'L':
                    ++counterForWhiteBishop;
                    break;
                case 'l':
                    ++counterForBlackBishop;
                    break;
                case 'T':
                    ++counterForWhiteRook;
                    break;
                case 't':
                    ++counterForBlackRook;
                    break;
                case 'B':
                    ++counterForWhitePawn;
                    break;
                case 'b':
                    ++counterForBlackPawn;
                    break;
                case 'S':
                    ++counterForWhiteKnight;
                    break;
                case 's':
                    ++counterForBlackKnight;
                    break;
                default:
                    ++counterForEmpty;

               }
            }
        if (counterForBlackKing != 1 || counterForWhiteKing != 1 || counterForBlackBishop > 2 ||
                counterForWhiteBishop > 2 || counterForBlackKnight > 2 || counterForWhiteKnight > 2 ||
                counterForBlackRook > 2 || counterForWhiteRook > 2 || counterForBlackPawn > 8 ||
                counterForWhitePawn > 8 || counterForBlackQueen > 1 || counterForWhiteQueen > 1) {
            return false;
        }
        return true;
    }
    */
}
