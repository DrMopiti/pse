package com.example.user.schachapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

    public void queenClicked() {
        Intent intent = new Intent(this, BoardActivity.class);
        intent.putExtra("clickedFigure", R.drawable.queen_figure_white);
        startActivity(intent);
    }

    public void rookClicked() {
        Intent intent = new Intent(this, BoardActivity.class);
        intent.putExtra("clickedFigure", R.drawable.rook_figure_white);
        startActivity(intent);
    }

    public void knightClicked() {
        Intent intent = new Intent(this, BoardActivity.class);
        intent.putExtra("clickedFigure", R.drawable.knight_figure_white);
        startActivity(intent);
    }

    public void bishopClicked() {
        Intent intent = new Intent(this, BoardActivity.class);
        intent.putExtra("clickedFigure", R.drawable.bishop_figure_white);
        startActivity(intent);
    }

}
