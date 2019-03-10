package com.example.user.schachapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.concurrent.ExecutionException;

/**
 * The type Main menu activity.
 * This Activity opens a MainMenuActivity.
 * The Player can choose between the buttons quick-match, search-player and statistics.
 */
public class MainMenuActivity extends AppCompatActivity {
    private Button buttonQuickMatch, buttonSearchPlayer, buttonStatistics, buttonChallenged;
    private SharedPreferences sharedPrefs;
    private SharedPreferences.Editor editor;
    private Boolean nameSet = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Looks if a user names is stored.
        sharedPrefs = getSharedPreferences("chessApp", 0);
        editor = sharedPrefs.edit();
       /* editor.remove("GesamtSpielAnzahl");
        editor.remove("Verloren");
        editor.remove("Gewonnen");
        editor.remove("Unentschieden");
        editor.remove("Username");
        editor.commit();*/
        if (sharedPrefs.getString("Username", "").length() < 1) {
            nameSet = true;
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        if(nameSet) {
            startService(new Intent(this, WebsocketService.class));
        }
        setContentView(com.example.user.schachapp.R.layout.activity_main_menu);
        buttonQuickMatch = findViewById(com.example.user.schachapp.R.id.buttonQuickMatch);
        buttonSearchPlayer = findViewById(com.example.user.schachapp.R.id.buttonSearchPlayer);
        buttonStatistics = findViewById(com.example.user.schachapp.R.id.buttonStatistics);
        buttonChallenged = findViewById(com.example.user.schachapp.R.id.lookChallenge);

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

        buttonChallenged.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                challengedClicked();

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

    public void challengedClicked() {
        AlertDialog.Builder a_builder = new AlertDialog.Builder(MainMenuActivity.this);
        String name = sharedPrefs.getString("Username", "NoUser");
        Boolean hasGame = false;
        try {
            hasGame = new HasGameTask().execute(name).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        if (hasGame){ //wenn ein spiel existiert für diesen spieler
            a_builder.setMessage("Spiel beitreten?").setCancelable(false) //Spielername einsetzen
                    .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(MainMenuActivity.this, BoardActivity.class); //Spieldaten mitgeben
                            intent.putExtra("isOnlineGame", true);
                            intent.putExtra("isWhite", false);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("Nein", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
        } else { //sonst
            a_builder.setMessage("Keine offenes Spiel");
        }
        AlertDialog challenged = a_builder.create();
        challenged.setTitle("Offenes Spiel");
        challenged.show();
    }
}
