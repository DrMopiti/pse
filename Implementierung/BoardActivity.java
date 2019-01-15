package com.example.user.schachapp;

import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class BoardActivity extends AppCompatActivity {
    private Button buttonDraw, buttonGiveUp;
    private ImageView chessboard;
    private DisplayMetrics dm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.user.schachapp.R.layout.activity_board);
        chessboard = findViewById(R.id.chessboard);
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

                int totalX = (int) event.getX(); // x-Koordinate der Berührung in Pixeln
                int totalY = (int) event.getY();
                int boardMetric = dm.widthPixels; // Breite des Displays in Pixeln
                int chessX = (totalX * 8 ) / boardMetric; // berechnet das Schachbrettfeld
                int chessY = 7 - ((totalY * 8 ) / boardMetric);
                String chessChar = "Außerhalb";
                try {
                    moveFigure((ImageView) findViewById(R.id.pawn_black_1), new Position(chessX, chessY));
                    moveFigure((ImageView) findViewById(R.id.king_black), new Position(chessX, chessY));
                    moveFigure((ImageView) findViewById(R.id.pawn_white_2), new Position(chessX, chessY));
                    chessChar = new Position(chessX, chessY).toString();
                } catch (IllegalPositionException e) {
                    e.printStackTrace();
                }
                txt.setText(chessChar);
                return true;
            }

            // Methode um Figuren auf dem Board zu bewegen
            public void moveFigure(ImageView iv, Position targetPosition) {
                float boardMetric = dm.widthPixels;
                int targetPosXPixels = Math.round(targetPosition.getX() * boardMetric / 8); // Entfernung des Ziels vom linken Schachbrettrand in Pixeln
                int targetPosYPixels = Math.round(targetPosition.getY() * boardMetric / 8); // Entfernung des Ziels vom oberen SChachbrettrand in Pixeln
                int xTilesToMove = targetPosXPixels - iv.getLeft(); // Pixel die man sich bewegen will
                int yTilesToMove = chessboard.getBottom() - iv.getBottom() - targetPosYPixels;
                ObjectAnimator animationX = ObjectAnimator.ofFloat(iv, "translationX", xTilesToMove);
                ObjectAnimator animationY = ObjectAnimator.ofFloat(iv, "translationY", yTilesToMove);
                animationX.setDuration(500);
                animationY.setDuration(500);
                animationX.start();
                animationY.start();
            }
        }
