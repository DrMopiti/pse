package com.example.user.schachapp;

import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.user.schachapp.chessLogic.BoardState;
import com.example.user.schachapp.chessLogic.ChessRuleProvider;
import com.example.user.schachapp.chessLogic.Move;
import com.example.user.schachapp.chessLogic.Piece;
import com.example.user.schachapp.chessLogic.Position;
import com.example.user.schachapp.chessLogic.Result;

import java.util.List;


public class BoardActivity extends AppCompatActivity {
    private Button buttonDraw, buttonGiveUp;
    private ImageView chessboard;
    private ImageView[][] savedPieces = new ImageView[8][8];
    private ImageView[][] pieces = new ImageView[8][8];
    private DisplayMetrics dm;
    private Position startPos = null;
    private BoardState board;
    private SharedPreferences sharedPrefs;
    private SharedPreferences.Editor editor;
    private ChessRuleProvider crp;
    private ClientSocket cs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.user.schachapp.R.layout.activity_board);
        chessboard = findViewById(R.id.chessboard);
        sharedPrefs = getSharedPreferences("chessApp", 0);
        editor = sharedPrefs.edit();
        cs = new ClientSocket(sharedPrefs.getString("Username", "noUserFound"));
        crp = new ChessRuleProvider();

        savedPieces[0][0] = findViewById(R.id.rook_black_1);
        savedPieces[1][0] = findViewById(R.id.knight_black_1);
        savedPieces[2][0] = findViewById(R.id.bishop_black_1);
        savedPieces[3][0] = findViewById(R.id.queen_black);
        savedPieces[4][0] = findViewById(R.id.king_black);
        savedPieces[5][0] = findViewById(R.id.bishop_black_2);
        savedPieces[6][0] = findViewById(R.id.knight_black_2);
        savedPieces[7][0] = findViewById(R.id.rook_black_2);
        savedPieces[0][1] = findViewById(R.id.pawn_black_1);
        savedPieces[1][1] = findViewById(R.id.pawn_black_2);
        savedPieces[2][1] = findViewById(R.id.pawn_black_3);
        savedPieces[3][1] = findViewById(R.id.pawn_black_4);
        savedPieces[4][1] = findViewById(R.id.pawn_black_5);
        savedPieces[5][1] = findViewById(R.id.pawn_black_6);
        savedPieces[6][1] = findViewById(R.id.pawn_black_7);
        savedPieces[7][1] = findViewById(R.id.pawn_black_8);
        savedPieces[0][6] = findViewById(R.id.pawn_white_1);
        savedPieces[1][6] = findViewById(R.id.pawn_white_2);
        savedPieces[2][6] = findViewById(R.id.pawn_white_3);
        savedPieces[3][6] = findViewById(R.id.pawn_white_4);
        savedPieces[4][6] = findViewById(R.id.pawn_white_5);
        savedPieces[5][6] = findViewById(R.id.pawn_white_6);
        savedPieces[6][6] = findViewById(R.id.pawn_white_7);
        savedPieces[7][6] = findViewById(R.id.pawn_white_8);
        savedPieces[0][7] = findViewById(R.id.rook_white_1);
        savedPieces[1][7] = findViewById(R.id.knight_white_1);
        savedPieces[2][7] = findViewById(R.id.bishop_white_1);
        savedPieces[4][7] = findViewById(R.id.king_white);
        savedPieces[3][7] = findViewById(R.id.queen_white);
        savedPieces[5][7] = findViewById(R.id.bishop_white_2);
        savedPieces[6][7] = findViewById(R.id.knight_white_2);
        savedPieces[7][7] = findViewById(R.id.rook_white_2);

        dm = getResources().getDisplayMetrics();

        buttonDraw = findViewById(com.example.user.schachapp.R.id.buttonDraw);
        buttonGiveUp = findViewById(com.example.user.schachapp.R.id.buttonGiveUp);

        //String boardString = cs.requestBoard(sharedPrefs.getString("Username", "noUserFound"));

        board = new BoardState("TB0000btSB0000bsLB0000blDB0000bdKB0000bkLB0000blSB0000bsTB0000bt##ttttt#0"/*boardString*/);

        Piece p = null;
        ImageView pieceIV = null;
            for (int i = 0; i < savedPieces.length; i++) {
                for (int j = 0; j < savedPieces[i].length; j++) {
                    p = board.getPieceAt(new Position(i,j));
                    if (p != null) {
                        pieceIV = getPieceIV(p.toString());
                        moveFigure(pieceIV, new Position(i,j), 0);
                        pieces[i][j] = pieceIV;
                        pieceIV.setVisibility(View.VISIBLE);
                    }
                }
            }


        Intent thisIntent = getIntent();
        if (thisIntent.getIntExtra("clickedFigure", 0) != 0) {
            int id = thisIntent.getIntExtra("clickedFigure", R.drawable.pawn_figure_white);
            for (int i = 0; i < savedPieces.length; i++) {
                if (board.getPieceAt(new Position(i,7)).toString().equals("B")) {
                    pieces[i][7].setImageResource(id);
                }
            }
        }

        if (crp.hasEnded(board)) {
            Result result = crp.getResult(board);
            String resultString = result.getResult();
            if (resultString.charAt(2) == '1') {
                int loses = Integer.valueOf(sharedPrefs.getString("Verloren", "0"));
                loses++;
                editor.putString("Verloren", String.valueOf(loses));
                editor.commit();
                Intent intent = new Intent(this, LostActivity.class);
                startActivity(intent);
            } else if (resultString.charAt(2) == '5') {
                int draws = Integer.valueOf(sharedPrefs.getString("Unentschieden", "0"));
                draws++;
                editor.putString("Unentschieden", String.valueOf(draws));
                editor.commit();
                Intent intent = new Intent(this, DrawActivity.class);
                startActivity(intent);
            }
        }

        buttonDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawClicked();
            }
        });

        buttonGiveUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                giveUpClicked();
            }
        });

        chessboard.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return boardClicked(event);
            }

        });
    }

    private void drawClicked() {
        AlertDialog.Builder a_builder = new AlertDialog.Builder(BoardActivity.this);
        a_builder.setMessage("Willst du das Unentschieden annehmen?").setCancelable(false)
                .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("Nein", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog draw = a_builder.create();
        draw.setTitle("Unentschieden");
        draw.show();
    }

    private void giveUpClicked() {
        AlertDialog.Builder a_builder = new AlertDialog.Builder(BoardActivity.this);
        a_builder.setMessage("Willst du wirklich Aufgeben?").setCancelable(false)
                .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("Nein", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog giveUp = a_builder.create();
        giveUp.setTitle("Aufgeben");
        giveUp.show();
    }

    // Board zeigt positionen an
    public boolean boardClicked(MotionEvent event) {
        int totalX = (int) event.getX(); // x-Koordinate der Berührung in Pixeln
        int totalY = (int) event.getY();
        int boardMetric = dm.widthPixels; // Breite des Displays in Pixeln
        int chessX = (totalX * 8 ) / boardMetric; // berechnet das Schachbrettfeld
        int chessY = 7 - ((totalY * 8 ) / boardMetric);
        tileSelected(chessX, chessY);
        return true;
    }

    // Methode um Figuren auf dem Board zu bewegen
    private void moveFigure(ImageView iv, Position targetPosition, int animationDuration) {
        float boardMetric = dm.widthPixels;
        int targetPosXPixels = Math.round(targetPosition.getX() * boardMetric / 8); // Entfernung des Ziels vom linken Schachbrettrand in Pixeln
        int targetPosYPixels = Math.round(targetPosition.getY() * boardMetric / 8); // Entfernung des Ziels vom oberen SChachbrettrand in Pixeln
        int xTilesToMove = targetPosXPixels - iv.getLeft(); // Pixel die man sich bewegen will
        int yTilesToMove = chessboard.getBottom() - iv.getBottom() - targetPosYPixels;
        ObjectAnimator animationX = ObjectAnimator.ofFloat(iv, "translationX", xTilesToMove);
        ObjectAnimator animationY = ObjectAnimator.ofFloat(iv, "translationY", yTilesToMove);
        animationX.setDuration(animationDuration);
        animationY.setDuration(animationDuration);
        animationX.start();
        animationY.start();
    }

    private void tileSelected(int x, int y) {
        Position positionClicked = null;
        try {
            positionClicked = new Position(x, y);
        } catch (IllegalArgumentException e) {

        }
        if (/*board.whiteToMove()*/true && positionClicked != null) {
            if ((startPos == null) && (board.getPieceAt(positionClicked) != null) && (board.getPieceAt(positionClicked).isWhite())) {
                startPos = positionClicked;
                showPosition();
            } else if ((startPos != null) && (!startPos.equals(positionClicked))){
                executeMove(positionClicked);
                startPos = null;
            }
        }
    }

    private void showPosition() {
         Piece selectedPiece = board.getPieceAt(startPos);
         if ((selectedPiece != null) && (pieces[startPos.getX()][startPos.getY()] != null)) {
             pieces[startPos.getX()][startPos.getY()].setColorFilter(Color.argb(100,0,0,255));
             System.out.println(selectedPiece.toString() + " " + startPos.toString() + " " + board.getPieceAt(startPos).toString());
             List<Move> moves = selectedPiece.getMovement(startPos, board);
             if (moves.size() > 0) {
                 colorMoves(moves);
             }
         }
    }

    private void executeMove(Position goal) {
         Piece selectedPiece = board.getPieceAt(startPos);
         List<Move> moves = selectedPiece.getMovement(startPos, board);
         ImageView piece = pieces[startPos.getX()][startPos.getY()];
         piece.setColorFilter(Color.argb(0,0,0,255));
         Move move = new Move(startPos, goal);
         if (movesContains(moves, move)) {
             if (pieces[goal.getX()][goal.getY()] != null) {
                 pieces[goal.getX()][goal.getY()].setVisibility(ImageView.INVISIBLE);
             }
             moveFigure(piece, goal, 500);
             pieces[goal.getX()][goal.getY()] = piece;
             pieces[startPos.getX()][startPos.getY()] = null;
             board.applyMove(move);
             //cs.sendMove(sharedPrefs.getString("Username", "noUserFound"), move.toString());
             if (crp.hasEnded(board)) {
                Result result = crp.getResult(board);
                String resultString = result.getResult();
                if (resultString.charAt(2) == '0') {
                    int wins = Integer.valueOf(sharedPrefs.getString("Gewonnen", "0"));
                    wins++;
                    editor.putString("Gewonnen", String.valueOf(wins));
                    editor.commit();
                    Intent intent = new Intent(this, WinnerActivity.class);
                    startActivity(intent);
                } else if (resultString.charAt(2) == '5') {
                    int draws = Integer.valueOf(sharedPrefs.getString("Unentschieden", "0"));
                    draws++;
                    editor.putString("Unentschieden", String.valueOf(draws));
                    editor.commit();
                    Intent intent = new Intent(this, DrawActivity.class);
                    startActivity(intent);
                }
             }
         }
        clearColores();
        if ((selectedPiece.toString().toLowerCase().equals("b")) && (move.getGoal().getY() == 6)) {
            Intent intent = new Intent(this, PawnActivity.class);
            startActivity(intent);
        }
    }

    private void clearColores() {
        ImageView toDraw = findViewById(R.id.toDraw);
        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        Bitmap bmp = Bitmap.createBitmap(chessboard.getWidth(), chessboard.getHeight(), conf);
        toDraw.setImageBitmap(bmp);
    }

    private void colorMoves(List<Move> moves) {
        ImageView toDraw = findViewById(R.id.toDraw);
        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        Bitmap bmp = Bitmap.createBitmap(chessboard.getWidth(), chessboard.getHeight(), conf); // this creates a MUTABLE bitmap
        Canvas c = new Canvas(bmp);
        Paint p = new Paint();
        p.setColor(Color.argb(70,0,0,255));
        float boardMetric = dm.widthPixels;
        Position position = new Position(0, 0);
        for (int i = 0; i < moves.size(); i++) {
            position = moves.get(i).getGoal();
            int targetPosXPixels = Math.round(position.getX() * boardMetric / 8); // Entfernung des Ziels vom linken Schachbrettrand in Pixeln
            int targetPosYPixels = Math.round(position.getY() * boardMetric / 8); // Entfernung des Ziels vom oberen Schachbrettrand in Pixeln
            c.drawRect(targetPosXPixels + boardMetric / 8,boardMetric - (targetPosYPixels + boardMetric / 8),targetPosXPixels, boardMetric - targetPosYPixels,p);
        }
        toDraw.setImageBitmap(bmp);
    }

    private ImageView getPieceIV(String pieceRepresentation) {
        ImageView pieceIV = null;
        switch (pieceRepresentation) {
            case "K":
                pieceIV = savedPieces[4][7];
                savedPieces[4][7] = null;
                break;
            case "k":
                pieceIV = savedPieces[4][0];
                savedPieces[4][0] = null;
                break;
            case "D":
                pieceIV = savedPieces[3][7];
                savedPieces[3][7] = null;
                break;
            case "d":
                pieceIV = savedPieces[3][0];
                savedPieces[3][0] = null;
                break;
            default:
                pieceIV = getPieceFromPieces(pieceRepresentation);
        }
        return pieceIV;
    }

    private ImageView getPieceFromPieces(String pieceRepresentation) {
        ImageView pieceIV = null;
        if (pieceRepresentation.equals("L")) {
                pieceIV = savedPieces[2][7];
                savedPieces[2][7] = null;
                if (pieceIV == null) {
                    pieceIV = savedPieces[5][7];
                    savedPieces[5][7] = null;
                }
        } else if (pieceRepresentation.equals("l")) {
            pieceIV = savedPieces[2][0];
            savedPieces[2][0] = null;
            if (pieceIV == null) {
                pieceIV = savedPieces[5][0];
                savedPieces[5][0] = null;
            }
        } else if (pieceRepresentation.equals("S")) {
            pieceIV = savedPieces[1][7];
            savedPieces[1][7] = null;
            if (pieceIV == null) {
                pieceIV = savedPieces[6][7];
                savedPieces[6][7] = null;
            }
        } else if (pieceRepresentation.equals("s")) {
            pieceIV = savedPieces[1][0];
            savedPieces[1][0] = null;
            if (pieceIV == null) {
                pieceIV = savedPieces[6][0];
                savedPieces[6][0] = null;
            }
        } else if (pieceRepresentation.equals("B")) {
            for (int i = 0; pieceIV == null; i++) {
                pieceIV = savedPieces[i][6];
                savedPieces[i][6] = null;
            }
        } else if (pieceRepresentation.equals("b")) {
            for (int i = 0; pieceIV == null; i++) {
                pieceIV = savedPieces[i][1];
                savedPieces[i][1] = null;
            }
        } else if (pieceRepresentation.equals("T")) {
            pieceIV = savedPieces[0][7];
            savedPieces[0][7] = null;
            if (pieceIV == null) {
                pieceIV = savedPieces[7][7];
                savedPieces[7][7] = null;
            }
        } else if (pieceRepresentation.equals("t")) {
            pieceIV = savedPieces[0][0];
            savedPieces[0][0] = null;
            if (pieceIV == null) {
                pieceIV = savedPieces[7][0];
                savedPieces[7][0] = null;
            }
        }
        return pieceIV;
    }

    private boolean movesContains(List<Move> moves, Move move) {
        for (int i = 0; i < moves.size(); i++) {
            if (moves.get(i).equals(move)) {
                return true;
            }
        }
        return false;
    }
}
