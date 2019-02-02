package com.example.user.schachapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * The type Pawn activity.
 * This Activity opens a Pawn-Transformation-Activity, when a player gets his pawn figure in the last basic row.
 * Then the player can change the pawn figure in an other figure like queen, rook, knight and bishop.
 */
public class PawnActivity extends AppCompatActivity {

    private Button queenButton, rookButton, knightButton, bishopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pawn);

        queenButton = findViewById(R.id.queenButton);
        rookButton = findViewById(R.id.rookButton);
        knightButton = findViewById(R.id.knightButton);
        bishopButton = findViewById(R.id.bishopButton);

        // clickListeners to change in the BoardActivity back.
        queenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queenClicked();
            }
        });

        rookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rookClicked();
            }
        });

        knightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                knightClicked();
            }
        });

        bishopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bishopClicked();
            }
        });
    }

    /**
     * When Queen is clicked, the figure pawn ist transformed in this figure.
     */
    public void queenClicked() {
        Intent intent = new Intent(this, BoardActivity.class);
        intent.putExtra("clickedFigure", R.drawable.queen_figure_white);
        startActivity(intent);
    }

    /**
     * When Rook is clicked, the figure pawn ist transformed in this figure.
     */
    public void rookClicked() {
        Intent intent = new Intent(this, BoardActivity.class);
        intent.putExtra("clickedFigure", R.drawable.rook_figure_white);
        startActivity(intent);
    }

    /**
     * When Knight is clicked, the figure pawn ist transformed in this figure.
     */
    public void knightClicked() {
        Intent intent = new Intent(this, BoardActivity.class);
        intent.putExtra("clickedFigure", R.drawable.knight_figure_white);
        startActivity(intent);
    }

    /**
     * When Bishop is clicked, the figure pawn ist transformed in this figure.
     */
    public void bishopClicked() {
        Intent intent = new Intent(this, BoardActivity.class);
        intent.putExtra("clickedFigure", R.drawable.bishop_figure_white);
        startActivity(intent);
    }

}
