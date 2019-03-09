package com.example.user.schachapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * The type Main menu activity.
 * This Activity opens a MainMenuActivity.
 * The Player can choose between the buttons quick-match, search-player and statistics.
 */
public class MainMenuActivity extends AppCompatActivity {
    private Button buttonQuickMatch, buttonSearchPlayer, buttonStatistics;
    private SharedPreferences sharedPrefs;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Looks if a user names is stored.
        sharedPrefs = getSharedPreferences("chessApp", 0);
        editor = sharedPrefs.edit();
        /*editor.remove("GesamtSpielAnzahl");
        editor.remove("Verloren");
        editor.remove("Gewonnen");
        editor.remove("Unentschieden");
        editor.remove("Username");
        editor.commit();*/
        startService(new Intent(this, WebsocketService.class));
        if (sharedPrefs.getString("Username", "").length() < 1) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        setContentView(com.example.user.schachapp.R.layout.activity_main_menu);
        buttonQuickMatch = findViewById(com.example.user.schachapp.R.id.buttonQuickMatch);
        buttonSearchPlayer = findViewById(com.example.user.schachapp.R.id.buttonSearchPlayer);
        buttonStatistics = findViewById(com.example.user.schachapp.R.id.buttonStatistics);

        // clickListener to change in the BoardActivity.
        buttonQuickMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quickMatchClicked();

            }
        });

        // clickListener to change in the SearchPlayerActivity.
        buttonSearchPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchPlayerClicked();
            }
        });

        // clickListener to change in the StatisticsActivity.
        buttonStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statisticsClicked();
            }
        });

    }

    /**
     * When Quick match is clicked, the player changes in the BoardActivity.
     * Also it stores allGames in the StatisticsActivity.
     */
    public void quickMatchClicked() {
        int allGames = sharedPrefs.getInt("GesamtSpielAnzahl", 0);
        allGames++;
        editor.putInt("GesamtSpielAnzahl", allGames);
        editor.commit();
        Intent intent = new Intent(this, BoardActivity.class);
        intent.putExtra("isOnlineGame", false);
        startActivity(intent);
    }

    /**
     * When Search player is clicked, the player changes in the SearchPlayerActivity.
     */
    public void searchPlayerClicked() {
        Intent intent = new Intent(this, SearchPlayerActivity.class);
        startActivity(intent);
    }

    /**
     * When Statistics is clicked, the player changes in the StatisticsActivity.
     */
    public void statisticsClicked() {
        Intent intent = new Intent(this, StatisticsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

    }
}
