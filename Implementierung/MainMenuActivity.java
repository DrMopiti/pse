package com.example.user.schachapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenuActivity extends AppCompatActivity {
    private Button buttonQuickMatch, buttonSearchPlayer, buttonStatistics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.user.schachapp.R.layout.activity_main_menu);
        buttonQuickMatch = findViewById(com.example.user.schachapp.R.id.buttonQuickMatch);
        buttonSearchPlayer = findViewById(com.example.user.schachapp.R.id.buttonSearchPlayer);
        buttonStatistics = findViewById(com.example.user.schachapp.R.id.buttonStatistics);

        buttonQuickMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quickMatchClicked();

            }
        });

        buttonSearchPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchPlayerClicked();
            }
        });

        buttonStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statisticsClicked();
            }
        });

    }

    public void quickMatchClicked() {
        Intent intent = new Intent(this, BoardActivity.class);
        startActivity(intent);
    }

    public void searchPlayerClicked() {
        Intent intent = new Intent(this, SearchPlayerActivity.class);
        startActivity(intent);
    }

    public void statisticsClicked() {
        Intent intent = new Intent(this, StatisticsActivity.class);
        startActivity(intent);
    }
}
