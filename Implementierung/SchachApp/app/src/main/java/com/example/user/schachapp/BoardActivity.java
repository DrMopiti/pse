package com.example.user.schachapp;

import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.schachapp.ClientSocket;
import com.example.user.schachapp.chessLogic.Move;
import com.example.user.schachapp.chessLogic.Position;
import com.example.user.schachapp.chessLogic.*;

import java.util.List;


public class BoardActivity extends AppCompatActivity {
    private Button buttonDraw, buttonGiveUp;
    private ImageView chessboard;
    private ImageView[] pieces = new ImageView[32];
    private DisplayMetrics dm;
    private Position startPos;
    private ClientSocket cs = new ClientSocket("HI");
    // private Game game = new Game(new User("user1"), new User("user2"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.user.schachapp.R.layout.activity_board);
        chessboard = findViewById(R.id.chessboard);
        pieces[0] = findViewById(R.id.rook_black_1);
        pieces[1] = findViewById(R.id.rook_black_2);
        pieces[2] = findViewById(R.id.bishop_black_1);
        pieces[3] = findViewById(R.id.bishop_black_2);
        pieces[4] = findViewById(R.id.knight_black_1);
        pieces[5] = findViewById(R.id.knight_black_2);
        pieces[6] = findViewById(R.id.pawn_black_1);
        pieces[7] = findViewById(R.id.pawn_black_2);
        pieces[8] = findViewById(R.id.pawn_black_3);
        pieces[9] = findViewById(R.id.pawn_black_4);
        pieces[10] = findViewById(R.id.pawn_black_5);
        pieces[11] = findViewById(R.id.pawn_black_6);
        pieces[12] = findViewById(R.id.pawn_black_7);
        pieces[13] = findViewById(R.id.pawn_black_8);
        pieces[14] = findViewById(R.id.queen_black);
        pieces[15] = findViewById(R.id.king_black);
        pieces[16] = findViewById(R.id.rook_white_1);
        pieces[17] = findViewById(R.id.rook_white_2);
        pieces[18] = findViewById(R.id.bishop_white_1);
        pieces[19] = findViewById(R.id.bishop_white_2);
        pieces[20] = findViewById(R.id.knight_white_1);
        pieces[21] = findViewById(R.id.knight_white_2);
        pieces[22] = findViewById(R.id.pawn_white_1);
        pieces[23] = findViewById(R.id.pawn_white_2);
        pieces[24] = findViewById(R.id.pawn_white_3);
        pieces[25] = findViewById(R.id.pawn_white_4);
        pieces[26] = findViewById(R.id.pawn_white_5);
        pieces[27] = findViewById(R.id.pawn_white_6);
        pieces[28] = findViewById(R.id.pawn_white_7);
        pieces[29] = findViewById(R.id.pawn_white_8);
        pieces[30] = findViewById(R.id.queen_white);
        pieces[31] = findViewById(R.id.king_white);
        //Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.king_figure_black);
        //pieces[22].setImageBitmap(bm);
        dm = getResources().getDisplayMetrics();

        buttonDraw = findViewById(com.example.user.schachapp.R.id.buttonDraw);
        buttonGiveUp = findViewById(com.example.user.schachapp.R.id.buttonGiveUp);

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

    public void drawClicked() {
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

    public void giveUpClicked() {
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

        TextView txt = findViewById(R.id.textView);
        final ImageView image = (ImageView) findViewById(R.id.bishop_black_1);

        int totalX = (int) event.getX(); // x-Koordinate der Berührung in Pixeln
        int totalY = (int) event.getY();
        int boardMetric = dm.widthPixels; // Breite des Displays in Pixeln
        int chessX = (totalX * 8 ) / boardMetric; // berechnet das Schachbrettfeld
        int chessY = 7 - ((totalY * 8 ) / boardMetric);
        String chessChar = "Außerhalb";
        moveFigure((ImageView) findViewById(R.id.pawn_black_1), new Position(chessX, chessY), 500);
        moveFigure((ImageView) findViewById(R.id.king_black), new Position(chessX, chessY), 500);
        moveFigure((ImageView) findViewById(R.id.pawn_white_2), new Position(chessX, chessY), 500);
        image.setColorFilter(Color.argb(100, 0, 0, 255));
        chessChar = new Position(chessX, chessY).toString();
        txt.setText(chessChar);

        colorMoves(null, new Position(chessX, chessY));

        return true;
    }

    // Methode um Figuren auf dem Board zu bewegen
    public void moveFigure(ImageView iv, Position targetPosition, int animationDuration) {
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

    public void tileSelected(int x, int y) {
        Position positionClicked = new Position(x, y);
        // game.whiteToMove();
        if (startPos == null) {
            startPos = positionClicked;
        } else {
            executeMove(positionClicked);
        }
    }

    public void showPosition(Position start) {
        // Piece selectedPiece = game.getPieceAt(start);
        // if (selectedPiece != null) {
        //     selectedPiece.getMovement(start, game.getBoard());
        // }
    }

    public void executeMove(Position goal) {

    }

    public void colorMoves(List<Move> moves, Position targetPosition) {
        ImageView toDraw = findViewById(R.id.toDraw);
        Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
        Bitmap bmp = Bitmap.createBitmap(chessboard.getWidth(), chessboard.getHeight(), conf); // this creates a MUTABLE bitmap
        Canvas c = new Canvas(bmp);
        Paint p = new Paint();
        p.setColor(Color.argb(70,0,0,255));
        float boardMetric = dm.widthPixels;
        int targetPosXPixels = Math.round(targetPosition.getX() * boardMetric / 8); // Entfernung des Ziels vom linken Schachbrettrand in Pixeln
        int targetPosYPixels = Math.round(targetPosition.getY() * boardMetric / 8); // Entfernung des Ziels vom oberen SChachbrettrand in Pixeln
        c.drawRect(targetPosXPixels + boardMetric / 8,boardMetric - (targetPosYPixels + boardMetric / 8),targetPosXPixels, boardMetric - targetPosYPixels,p);
        toDraw.setImageBitmap(bmp);
    }

    public ImageView[] getPieces() {
        return pieces;
    }

    public ImageView getPieceIV(String pieceRepresentation) {
        ImageView pieceIV;
        switch (pieceRepresentation) {
            case "K":
                pieceIV = pieces[31];
                pieces[31] = null;
                break;
            case "k":
                pieceIV = pieces[15];
                pieces[15] = null;
                break;
            case "D":
                pieceIV = pieces[30];
                pieces[30] = null;
                break;
            case "d":
                pieceIV = pieces[14];
                pieces[14] = null;
                break;
            default:
                pieceIV = getPieceFromPieces(pieceRepresentation);
        }
        return pieceIV;
    }

    private ImageView getPieceFromPieces(String pieceRepresentation) {
        ImageView pieceIV = null;
        if (pieceRepresentation.equals("L")) {
            for (int i = 18; pieces[i] == null; i++) {
                pieceIV = pieces[i];
                pieces[i] = null;
            }
        } else if (pieceRepresentation.equals("l")) {
            for (int i = 2; pieces[i] == null; i++) {
                pieceIV = pieces[i];
                pieces[i] = null;
            }
        } else if (pieceRepresentation.equals("S")) {
            for (int i = 20; pieces[i] == null; i++) {
                pieceIV = pieces[i];
                pieces[i] = null;
            }
        } else if (pieceRepresentation.equals("s")) {
            for (int i = 4; pieces[i] == null; i++) {
                pieceIV = pieces[i];
                pieces[i] = null;
            }
        } else if (pieceRepresentation.equals("B")) {
            for (int i = 22; pieces[i] == null; i++) {
                pieceIV = pieces[i];
                pieces[i] = null;
            }
        } else if (pieceRepresentation.equals("b")) {
            for (int i = 6; pieces[i] == null; i++) {
                pieceIV = pieces[i];
                pieces[i] = null;
            }
        } else if (pieceRepresentation.equals("T")) {
            for (int i = 16; pieces[i] == null; i++) {
                pieceIV = pieces[i];
                pieces[i] = null;

            }
        } else if (pieceRepresentation.equals("t")) {
            for (int i = 0; pieces[i] == null; i++) {
                pieceIV = pieces[i];
                pieces[i] = null;
            }
        }
        return pieceIV;
    }
}
