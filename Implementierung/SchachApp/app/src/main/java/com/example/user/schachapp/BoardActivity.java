package com.example.user.schachapp;

import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.constraint.ConstraintLayout;
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
import com.example.user.schachapp.chessLogic.MoveFactory;
import com.example.user.schachapp.chessLogic.Piece;
import com.example.user.schachapp.chessLogic.PieceFactory;
import com.example.user.schachapp.chessLogic.Position;
import com.example.user.schachapp.chessLogic.Promotion;
import com.example.user.schachapp.chessLogic.Result;

import java.util.List;


/**
 * The type Board activity.
 * This Activity opens, when a player clicks the Button quick-match.
 */
public class BoardActivity extends AppCompatActivity {
    private Button buttonDraw, buttonGiveUp;
    private ImageView chessboard;
    private ImageView[] savedPieces = new ImageView[32];
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
        // store.
        sharedPrefs = getSharedPreferences("chessApp", 0);
        editor = sharedPrefs.edit();
        cs = new ClientSocket(sharedPrefs.getString("Username", "noUserFound"));
        cs.connectToWS();
        crp = new ChessRuleProvider();

        // two-dimensional array for the chess-pieces.
        savedPieces[0] = findViewById(R.id.figure_1);
        savedPieces[1] = findViewById(R.id.figure_3);
        savedPieces[2] = findViewById(R.id.figure_5);
        savedPieces[3] = findViewById(R.id.figure_7);
        savedPieces[4] = findViewById(R.id.figure_8);
        savedPieces[5] = findViewById(R.id.figure_6);
        savedPieces[6] = findViewById(R.id.figure_4);
        savedPieces[7] = findViewById(R.id.figure_2);
        savedPieces[8] = findViewById(R.id.figure_9);
        savedPieces[9] = findViewById(R.id.figure_10);
        savedPieces[10] = findViewById(R.id.figure_11);
        savedPieces[11] = findViewById(R.id.figure_12);
        savedPieces[12] = findViewById(R.id.figure_13);
        savedPieces[13] = findViewById(R.id.figure_14);
        savedPieces[14] = findViewById(R.id.figure_15);
        savedPieces[15] = findViewById(R.id.figure_16);
        savedPieces[16] = findViewById(R.id.figure_17);
        savedPieces[17] = findViewById(R.id.figure_18);
        savedPieces[18] = findViewById(R.id.figure_19);
        savedPieces[19] = findViewById(R.id.figure_20);
        savedPieces[20] = findViewById(R.id.figure_21);
        savedPieces[21] = findViewById(R.id.figure_22);
        savedPieces[22] = findViewById(R.id.figure_23);
        savedPieces[23] = findViewById(R.id.figure_24);
        savedPieces[24] = findViewById(R.id.figure_25);
        savedPieces[25] = findViewById(R.id.figure_27);
        savedPieces[26] = findViewById(R.id.figure_29);
        savedPieces[27] = findViewById(R.id.figure_32);
        savedPieces[28] = findViewById(R.id.figure_31);
        savedPieces[29] = findViewById(R.id.figure_30);
        savedPieces[30] = findViewById(R.id.figure_28);
        savedPieces[31] = findViewById(R.id.figure_26);

        dm = getResources().getDisplayMetrics();

        buttonDraw = findViewById(com.example.user.schachapp.R.id.buttonDraw);
        buttonGiveUp = findViewById(com.example.user.schachapp.R.id.buttonGiveUp);

        String boardString = "TB0000bt" +
                "SB0000bs" +
                "LB0000bl" +
                "DB0000bd" +
                "KB0000bk" +
                "LB0000bl" +
                "SB0000bs" +
                "TB0000bt" +
                "##ttttt#0";//cs.requestBoard(sharedPrefs.getString("Username", "noUserFound"));

        board = new BoardState(boardString);

        // checks if there is an pawn-transformation and does it.
        Intent thisIntent = getIntent();
        if (thisIntent.getIntExtra("clickedFigure", 0) != 0) {
            Piece piece = null;
            int id = thisIntent.getIntExtra("clickedFigure", R.drawable.pawn_figure_white);
            String move = thisIntent.getStringExtra("move");
            switch (id) {
                case R.drawable.queen_figure_white:
                    move += "-" + "D";
                    break;
                case R.drawable.rook_figure_white:
                    move += "-" + "T";
                    break;
                case R.drawable.knight_figure_white:
                    move += "-" + "S";
                    break;
                case R.drawable.bishop_figure_white:
                    move += "-" + "L";
                    break;
            }
            Move theMove = MoveFactory.getMove(move);
            board.applyMove(theMove);
            //cs.sendMove(sharedPrefs.getString("Username", "noUserFound"), move.toString());
            paintBoard(board);
            pieces[theMove.getGoal().getX()][7].setImageResource(id);
        } else {
            paintBoard(board);
        }

        // checks if the other player has an los or an draw on the chessBoard and change to the appropriate Activity.
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

        // clickListeners to open an dialog.
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

    // when the draw button is clicked, then there opens an Dialog.
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

    // when the giveUp button is clicked, then there opens an Dialog.
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

    /**
     * Board clicked boolean.
     * This method receives a MotionEvent and calculates the field on the chessboard, based on the touched pixels
     *
     * @param event the event that receives the touch on the display
     * @return the boolean true
     */
    private boolean boardClicked(MotionEvent event) {
        int totalX = (int) event.getX(); // x-coordinate from the touch in pixel.
        int totalY = (int) event.getY();
        int boardMetric = dm.widthPixels; // width from the display in pixel.
        int chessX = (totalX * 8 ) / boardMetric; // calculates the chess-board.
        int chessY = 7 - ((totalY * 8 ) / boardMetric);
        tileSelected(chessX, chessY);
        return true;
    }

    // method to move the figures on the chessboard using ObjectAnimator.
    private void moveFigure(ImageView iv, Position targetPosition, int animationDuration) {
        float boardMetric = dm.widthPixels;
        // distance from the target counting from the left side of the chessboard in pixels.
        int targetPosXPixels = Math.round(targetPosition.getX() * boardMetric / 8);
        // distance from the target counting from the top side of the chessboard in pixels.
        int targetPosYPixels = Math.round(targetPosition.getY() * boardMetric / 8);
        // calculates the distance between the current position of the figure and its goal.
        int xTilesToMove = targetPosXPixels - iv.getLeft();
        int yTilesToMove = chessboard.getBottom() - iv.getBottom() - targetPosYPixels;
        ObjectAnimator animationX = ObjectAnimator.ofFloat(iv, "translationX", xTilesToMove);
        ObjectAnimator animationY = ObjectAnimator.ofFloat(iv, "translationY", yTilesToMove);
        animationX.setDuration(animationDuration);
        animationY.setDuration(animationDuration);
        animationX.start();
        animationY.start();
        // sets the right size for the figures
        ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) iv.getLayoutParams();
        lp.bottomMargin = 0;
        lp.leftMargin = 0;
        lp.rightMargin = 0;
        lp.topMargin = Math.round((boardMetric * 7) / 8);
        lp.setMarginEnd(360);
        lp.setMarginStart(0);
    }

    // checks if the player selected a figure or a goal and calls the corresponding method.
    private void tileSelected(int x, int y) {
        Position positionClicked = null;
        try {
            positionClicked = new Position(x, y);
        } catch (IllegalArgumentException e) {

        }
        if (positionClicked != null) {
            if ((startPos == null) && (board.getPieceAt(positionClicked) != null) && (board.getPieceAt(positionClicked).isWhite() == board.whiteToMove())) {
                startPos = positionClicked;
                showPosition();
            } else if ((startPos != null) && (!startPos.equals(positionClicked))){
                executeMove(positionClicked);
                startPos = null;
            }
        }
    }

    // colors the selected piece and passes the possible moves of it to colorMoves().
    private void showPosition() {
         Piece selectedPiece = board.getPieceAt(startPos);
         if ((selectedPiece != null) && (pieces[startPos.getX()][startPos.getY()] != null)) {
             pieces[startPos.getX()][startPos.getY()].setColorFilter(Color.argb(100,0,0,255));
             List<Move> moves =crp.getLegalMoves(startPos, board);
             if (moves.size() > 0) {
                 colorMoves(moves);
             }
         }
    }

    // checks if the chosen move is possible and then executes it. Then checks if the game has ended and calls clearColors().
    private void executeMove(Position goal) {
         clearColors();
         Piece selectedPiece = board.getPieceAt(startPos);
         List<Move> moves = crp.getLegalMoves(startPos, board);
         ImageView piece = pieces[startPos.getX()][startPos.getY()];
         piece.setColorFilter(Color.argb(0,0,0,255));
         Move move = new Move(startPos, goal);
         if (movesContains(moves, move)) {
             // checks if in this move a figure was captured and removes it.
             if (pieces[goal.getX()][goal.getY()] != null) {
                 pieces[goal.getX()][goal.getY()].setVisibility(ImageView.INVISIBLE);
             }
             moveFigure(piece, goal, 500);
             pieces[goal.getX()][goal.getY()] = piece;
             pieces[startPos.getX()][startPos.getY()] = null;
             // checks if there should happen a pawn-transformation.
             if ((selectedPiece.toString().toLowerCase().equals("b")) && (move.getGoal().getY() == 7)) {
                 Intent intent = new Intent(this, PawnActivity.class);
                 intent.putExtra("move", move.toString());
                 startActivity(intent);
             }
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
    }

    // removes the coloring from the possible moves.
    private void clearColors() {
        ImageView toDraw = findViewById(R.id.toDraw);
        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        Bitmap bmp = Bitmap.createBitmap(chessboard.getWidth(), chessboard.getHeight(), conf);
        toDraw.setImageBitmap(bmp);
    }

    // colors all possible moves by drawing matching rectangles in another ImageView layer.
    private void colorMoves(List<Move> moves) {
        ImageView toDraw = findViewById(R.id.toDraw);
        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        Bitmap bmp = Bitmap.createBitmap(chessboard.getWidth(), chessboard.getHeight(), conf);
        Canvas c = new Canvas(bmp);
        Paint p = new Paint();
        p.setColor(Color.argb(70,0,0,255));
        float boardMetric = dm.widthPixels;
        Position position = new Position(0, 0);
        for (int i = 0; i < moves.size(); i++) {
            position = moves.get(i).getGoal();
            // calculates the position in pixels, where the possible move has its goal.
            int targetPosXPixels = Math.round(position.getX() * boardMetric / 8);
            int targetPosYPixels = Math.round(position.getY() * boardMetric / 8);
            c.drawRect(targetPosXPixels + boardMetric / 8,boardMetric - (targetPosYPixels + boardMetric / 8),targetPosXPixels, boardMetric - targetPosYPixels,p);
        }
        toDraw.setImageBitmap(bmp);
    }

    // returns the ImageView matching to the given string-representation.
    private ImageView getPieceIV(String pieceRepresentation) {
        ImageView pieceIV = null;
        for (int i = 0; pieceIV == null; i++) {
            pieceIV = savedPieces[i];
            savedPieces[i] = null;
        }
        switch (pieceRepresentation) {
            case "K":
                pieceIV.setImageResource(R.drawable.king_figure_white);
                break;
            case "k":
                pieceIV.setImageResource(R.drawable.king_figure_black);
                break;
            case "D":
                pieceIV.setImageResource(R.drawable.queen_figure_white);
                break;
            case "d":
                pieceIV.setImageResource(R.drawable.queen_figure_black);
                break;
            case "L":
                pieceIV.setImageResource(R.drawable.bishop_figure_white);
                break;
            case "l":
                pieceIV.setImageResource(R.drawable.bishop_figure_black);
                break;
            case "S":
                pieceIV.setImageResource(R.drawable.knight_figure_white);
                break;
            case "s":
                pieceIV.setImageResource(R.drawable.knight_figure_black);
                break;
            case "T":
                pieceIV.setImageResource(R.drawable.rook_figure_white);
                break;
            case "t":
                pieceIV.setImageResource(R.drawable.rook_figure_black);
                break;
            case "B":
                pieceIV.setImageResource(R.drawable.pawn_figure_white);
                break;
            case "b":
                pieceIV.setImageResource(R.drawable.pawn_figure_black);
                break;
        }
        return pieceIV;
    }

    // checks if the given list contains the given move
    private boolean movesContains(List<Move> moves, Move move) {
        for (int i = 0; i < moves.size(); i++) {
            if (moves.get(i).equals(move)) {
                return true;
            }
        }
        return false;
    }

    private void paintBoard(BoardState board) {
        // runs through the array and moves the imageViews form the pieces to the positions that is set from the board.
        Piece p = null;
        ImageView pieceIV = null;
        for (int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces[i].length; j++) {
                p = board.getPieceAt(new Position(i,j));
                if (p != null) {
                    pieceIV = getPieceIV(p.toString());
                    moveFigure(pieceIV, new Position(i,j), 0);
                    pieces[i][j] = pieceIV;
                    pieceIV.setVisibility(View.VISIBLE);
                }
            }
        }
    }
}
