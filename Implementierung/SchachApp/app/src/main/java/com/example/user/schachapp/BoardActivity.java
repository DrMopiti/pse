package com.example.user.schachapp;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.StrictMode;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.user.schachapp.chessLogic.BoardState;
import com.example.user.schachapp.chessLogic.Castling;
import com.example.user.schachapp.chessLogic.ChessRuleProvider;
import com.example.user.schachapp.chessLogic.EnPassant;
import com.example.user.schachapp.chessLogic.Move;
import com.example.user.schachapp.chessLogic.MoveFactory;
import com.example.user.schachapp.chessLogic.Piece;
import com.example.user.schachapp.chessLogic.Position;
import com.example.user.schachapp.chessLogic.Result;

import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * The type Board activity.
 * This Activity opens, when a player clicks the Button quick-match.
 */
public class BoardActivity extends AppCompatActivity {
    private Button buttonDraw, buttonGiveUp;
    private ImageView chessboard;
    private int[] savedPieces = new int[32];
    private int[][] pieces = new int[8][8];
    private DisplayMetrics dm;
    private Position selectedPos = null;
    private BoardState board;
    private String move;
    private ChessRuleProvider crp;
    private ClientSocket cs;
    private boolean isOnlineGame;
    private boolean isWhite;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /**
         * NICHT NACHMACHEN!!!!!
         */
       // StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
      //  StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(com.example.user.schachapp.R.layout.activity_board);
        dm = getResources().getDisplayMetrics();
        chessboard = findViewById(R.id.chessboard);
        SharedPreferences sharedPrefs = getSharedPreferences("chessApp", 0);
        cs = new ClientSocket();
        Intent thisIntent = getIntent();
        isOnlineGame = thisIntent.getBooleanExtra("isOnlineGame", false);
        isWhite = thisIntent.getBooleanExtra("isWhite", true);
        //isOnlineGame = false;

        crp = new ChessRuleProvider();

        // one-dimensional array for the chess-pieces.
        savedPieces[0] = R.id.figure_1;
        savedPieces[1] = R.id.figure_2;
        savedPieces[2] = R.id.figure_3;
        savedPieces[3] = R.id.figure_4;
        savedPieces[4] = R.id.figure_5;
        savedPieces[5] = R.id.figure_6;
        savedPieces[6] = R.id.figure_7;
        savedPieces[7] = R.id.figure_8;
        savedPieces[8] = R.id.figure_9;
        savedPieces[9] = R.id.figure_10;
        savedPieces[10] = R.id.figure_11;
        savedPieces[11] = R.id.figure_12;
        savedPieces[12] = R.id.figure_13;
        savedPieces[13] = R.id.figure_14;
        savedPieces[14] = R.id.figure_15;
        savedPieces[15] = R.id.figure_16;
        savedPieces[16] = R.id.figure_17;
        savedPieces[17] = R.id.figure_18;
        savedPieces[18] = R.id.figure_19;
        savedPieces[19] = R.id.figure_20;
        savedPieces[20] = R.id.figure_21;
        savedPieces[21] = R.id.figure_22;
        savedPieces[22] = R.id.figure_23;
        savedPieces[23] = R.id.figure_24;
        savedPieces[24] = R.id.figure_25;
        savedPieces[25] = R.id.figure_26;
        savedPieces[26] = R.id.figure_27;
        savedPieces[27] = R.id.figure_28;
        savedPieces[28] = R.id.figure_29;
        savedPieces[29] = R.id.figure_30;
        savedPieces[30] = R.id.figure_31;
        savedPieces[31] = R.id.figure_32;

        buttonDraw = findViewById(com.example.user.schachapp.R.id.buttonDraw);
        buttonGiveUp = findViewById(com.example.user.schachapp.R.id.buttonGiveUp);

        if (isOnlineGame) {
			try {
                String boardString = new GetBoardTask().execute(sharedPrefs.getString("Username", "")).get();
                //Toast.makeText(this, boardString, Toast.LENGTH_LONG).show();
                //System.out.println(boardString);

                board = new BoardState(boardString);
               //board = crp.getStartState();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
		} else {
            if (thisIntent.getStringExtra("board") != null) {
                board = new BoardState(thisIntent.getStringExtra("board"));
            } else {
                board = crp.getStartState();
            }
        }

        // checks if there is an pawn-transformation and does it.
        if (thisIntent.getIntExtra("clickedFigure", 0) != 0) {
            Piece piece = null;
            int id = thisIntent.getIntExtra("clickedFigure", R.drawable.pawn_figure_white);
            move = thisIntent.getStringExtra("move");
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
                case R.drawable.queen_figure_black:
                    move += "-" + "d";
                    break;
                case R.drawable.rook_figure_black:
                    move += "-" + "t";
                    break;
                case R.drawable.knight_figure_black:
                    move += "-" + "s";
                    break;
                case R.drawable.bishop_figure_black:
                    move += "-" + "l";
                    break;
            }
            Move theMove = MoveFactory.getMove(move);
            board.applyMove(theMove);
            if (isOnlineGame) {
                ThreadHandler th = new ThreadHandler();
                th.runInBackground(new Runnable() {
                    @Override
                    public void run() {
                        SharedPreferences sharedPrefs = getSharedPreferences("chessApp", 0);
                        cs.sendMove(sharedPrefs.getString("Username", "noUserFound"), move);
                    }
                });
            }
                move = "";
                paintBoard(board);
                ImageView iv = findViewById(pieces[theMove.getGoal().getX()][theMove.getGoal().getY()]);
                iv.setImageResource(id);
            } else {
                paintBoard(board);
            }

            // checks if the other player has an los or an draw on the chessBoard and change to the appropriate Activity.
            if (crp.hasEnded(board)) {
                getResult();
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
        a_builder.setMessage("Willst du Unentschieden anbieten?").setCancelable(false)
                .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (isOnlineGame) {
                            if (isWhite) {
                                board.setWhiteDrawOffer(true);
                            } else {
                                board.setBlackDrawOffer(true);
                            }
                        }
                        dialog.cancel();
                     //   Intent intent = new Intent(BoardActivity.this, DrawActivity.class);
                     //   startActivity(intent);
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
                        if (isOnlineGame) {
                            if (isWhite) {
                                board.setWhiteSurrender();
                            } else {
                                board.setBlackSurrender();
                            }
                        }
                        Intent intent = new Intent(BoardActivity.this, LostActivity.class);
                        startActivity(intent);
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

    private void checkForEnd(BoardState board) {
        if (board.hasWhiteSurrender() && !isWhite) {
            Intent intent = new Intent(BoardActivity.this, WinnerActivity.class);
            startActivity(intent);
        }
        if (board.hasBlackSurrender() && isWhite) {
            Intent intent = new Intent(BoardActivity.this, WinnerActivity.class);
            startActivity(intent);
        }
        if ((board.isWhiteDrawOffer() && !isWhite) || (board.isBlackDrawOffer() && isWhite)) {
            AlertDialog.Builder a_builder = new AlertDialog.Builder(BoardActivity.this);
            a_builder.setMessage("Willst du das Unentschieden annehmen?").setCancelable(false)
                    .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(BoardActivity.this, DrawActivity.class);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("Nein", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            board.setBlackDrawOffer(false);
            board.setWhiteDrawOffer(false);
            board.setDraw();
            AlertDialog draw = a_builder.create();
            draw.setTitle("Unentschieden");
            draw.show();
        }

        if (board.isDraw()) {
            Intent intent = new Intent(BoardActivity.this, DrawActivity.class);
            startActivity(intent);
        }
    }
    /**
     * Board clicked boolean.
     * This method receives a MotionEvent and calculates the field on the chessboard, based on the touched pixels
     *
     * @param event the event that receives the touch on the display
     * @return the boolean true
     */
    private boolean boardClicked(MotionEvent event) {
        /*if (isOnlineGame && board.whiteToMove() != isWhite) {
            SharedPreferences sharedPrefs = getSharedPreferences("chessApp", 0);
            try {
                String boardString = new GetBoardTask().execute(sharedPrefs.getString("Username", "")).get();
                board = new BoardState(boardString);
                checkForEnd(board);
                paintBoard(board);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }*/
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
        if (isOnlineGame) {
            if (board.whiteToMove() != isWhite) {
                return;
            }
        }
        if (positionClicked != null) {
                if ((selectedPos == null) && (board.getPieceAt(positionClicked) != null) && (board.getPieceAt(positionClicked).isWhite() == board.whiteToMove())) {
                    selectedPos = positionClicked;
                    showPosition();
                } else if ((selectedPos != null) && (!selectedPos.equals(positionClicked))) {
                    executeMove(positionClicked);
                    selectedPos = null;
                }
        }
    }

    // colors the selected piece and passes the possible moves of it to colorMoves().
    private void showPosition() {
         Piece selectedPiece = board.getPieceAt(selectedPos);
         if ((selectedPiece != null) && (pieces[selectedPos.getX()][selectedPos.getY()] != 0)) {
             ImageView iv = findViewById(pieces[selectedPos.getX()][selectedPos.getY()]);
             iv.setColorFilter(Color.argb(100,0,0,255));
             List<Move> moves = crp.getLegalMoves(selectedPos, board);
             if (moves.size() > 0) {
                 colorMoves(moves);
             }
         }
    }

    // checks if the chosen move is possible and then executes it. Then checks if the game has ended and calls clearColors().
    private void executeMove(Position goal) {
         clearColors();
         Piece selectedPiece = board.getPieceAt(selectedPos);
         List<Move> moves = crp.getLegalMoves(selectedPos, board);
         ImageView piece = findViewById(pieces[selectedPos.getX()][selectedPos.getY()]);
         piece.setColorFilter(Color.argb(0,0,0,255));
        Move theMove = getMoveByGoal(moves, goal);
        if (theMove != null) {
             // checks if in this move a figure was captured and removes it.
             if (pieces[goal.getX()][goal.getY()] != 0) {
                 findViewById(pieces[goal.getX()][goal.getY()]).setVisibility(ImageView.INVISIBLE);
             }
             moveFigure(piece, goal, 500);
             pieces[goal.getX()][goal.getY()] = pieces[selectedPos.getX()][selectedPos.getY()];
             pieces[selectedPos.getX()][selectedPos.getY()] = 0;
            if (theMove instanceof Castling) {
                Position rookStart = ((Castling) theMove).getRookMove().getStart();
                Position rookGoal = ((Castling) theMove).getRookMove().getGoal();
                ImageView rook = findViewById(pieces[rookStart.getX()][rookStart.getY()]);
                moveFigure(rook, rookGoal, 500);
                pieces[rookGoal.getX()][rookGoal.getY()] = pieces[rookStart.getX()][rookStart.getY()];
                pieces[rookStart.getX()][rookStart.getY()] = 0;
            }

            if (theMove instanceof EnPassant) {
                Position removePawn = ((EnPassant) theMove).getRemovePawn();
                findViewById(pieces[removePawn.getX()][removePawn.getY()]).setVisibility(ImageView.INVISIBLE);
                pieces[removePawn.getX()][removePawn.getY()] = 0;
            }
             // checks if there should happen a pawn-transformation of white.
             if ((selectedPiece.toString().toLowerCase().equals("b")) && (theMove.getGoal().getY() == 7)) {
                 Intent intent = new Intent(this, WhitePawnActivity.class);
                 intent.putExtra("move", theMove.toString());
                 intent.putExtra("board", board.toString());
                 startActivity(intent);
             }
             // checks if there should happen a pawn-transformation of black.
             if ((selectedPiece.toString().toLowerCase().equals("b")) && (theMove.getGoal().getY() == 0)) {
                 Intent intent = new Intent(this, BlackPawnActivity.class);
                 intent.putExtra("move", theMove.toString());
                 intent.putExtra("board", board.toString());
                 startActivity(intent);
             }
             board.applyMove(theMove);
             move = theMove.toString();
             if (isOnlineGame) {
                 ThreadHandler th = new ThreadHandler();
                 Runnable r = new Runnable() {
                     @Override
                     public void run() {
                         SharedPreferences sharedPrefs = getSharedPreferences("chessApp", 0);
                         cs.sendMove(sharedPrefs.getString("Username", "noUserFound"), move);
                     }
                 };
                 th.runInBackground(r);
             }
             if (crp.hasEnded(board)) {
                getResult();
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
    private ImageView getPieceIV(String pieceRepresentation, int ivCounter) {
        ImageView pieceIV = findViewById(savedPieces[ivCounter]);
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

    // returns the matching move from list
    private Move getMoveByGoal(List<Move> moves, Position goal) {
        for (Move m : moves) {
            if (m.getGoal().equals(goal)) {
                return m;
            }
        }
        return null;
    }

    /**
     * runs through the array and moves the imageViews form the pieces to the positions that is set from the board.
     *
     * @param board to be painted
     */
    public void paintBoard(BoardState board) {
        Piece p = null;
        ImageView pieceIV = null;
        int ivCounter = 0;
        for (int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces[i].length; j++) {
                p = board.getPieceAt(new Position(i,j));
                if (p != null) {
                    pieceIV = getPieceIV(p.toString(), ivCounter);
                    moveFigure(pieceIV, new Position(i,j), 0);
                    pieces[i][j] = pieceIV.getId();
                    pieceIV.setVisibility(View.VISIBLE);
                    ivCounter++;
                    if (!isOnlineGame && !p.isWhite()) {
                        pieceIV.setRotation(180);
                    }
                }
            }
        }
    }

    private void getResult() {
        Result result = crp.getResult(board);
        String resultString = result.getResult();
        SharedPreferences sharedPrefs = getSharedPreferences("chessapp", 0);
        if (resultString.charAt(2) == '1') {
            if (isOnlineGame) {
                if (isWhite) {
                    int loses = sharedPrefs.getInt("Verloren", 0);
                    loses++;
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    editor.putInt("Verloren", loses);
                    editor.commit();
                } else {
                    int wins = sharedPrefs.getInt("Gewonnen", 0);
                    wins++;
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    editor.putInt("Gewonnen", wins);
                    editor.commit();
                }
            }
            Intent intent = new Intent(this, LostActivity.class);
            startActivity(intent);

        } else if (resultString.charAt(2) == '5') {
            if (isOnlineGame) {
                int draws = sharedPrefs.getInt("Unentschieden", 0);
                draws++;
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putInt("Unentschieden", draws);
                editor.commit();
            }
            Intent intent = new Intent(this, DrawActivity.class);
            startActivity(intent);

        } else if (resultString.charAt(2) == '0') {
            if (isOnlineGame) {
                if (isWhite) {
                    int wins = sharedPrefs.getInt("Gewonnen", 0);
                    wins++;
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    editor.putInt("Gewonnen", wins);
                    editor.commit();
                } else {
                    int loses = sharedPrefs.getInt("Verloren", 0);
                    loses++;
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    editor.putInt("Verloren", loses);
                    editor.commit();
                }
            }
            Intent intent = new Intent(this, WinnerActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        this.recreate();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestoreInstanceState (Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
    }
}
