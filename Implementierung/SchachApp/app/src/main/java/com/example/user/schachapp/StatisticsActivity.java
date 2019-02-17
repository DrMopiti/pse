package com.example.user.schachapp;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * The type Statistics activity.
 * This Activity opens a StatisticsActivity, when the player clicks the button Statistics.
 * There can the player see his Statistics.
 */
public class StatisticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // store all values for the statistics.
        SharedPreferences sharedPrefs = getSharedPreferences("chessApp", 0);
        setContentView(com.example.user.schachapp.R.layout.activity_statistics);
        TextView username = findViewById(R.id.usernameText);
        TextView gewonnen = findViewById(R.id.gewonnenZahl);
        TextView verloren = findViewById(R.id.verlorenZahl);
        TextView gewinnrate = findViewById(R.id.gewinnrateZahl);
        TextView unentschieden = findViewById(R.id.unentschiedenZahl);
        TextView gesamtSpielZahl = findViewById(R.id.gesamtspielanzahlZahl);

        username.setText(sharedPrefs.getString("Username", ""));
        int wonGames = sharedPrefs.getInt("Gewonnen", 0);
        int allGames = sharedPrefs.getInt("GesamtSpielAnzahl", 0);
        gewonnen.setText(String.valueOf(wonGames));
        verloren.setText(String.valueOf(sharedPrefs.getInt("Verloren", 0)));
        // calculates the winRate for the player
        float won = Integer.valueOf(wonGames);
        float all = Integer.valueOf(allGames);
        float winRate = Math.round((won / all) * 10000);
        winRate = winRate / 100;
        gewinnrate.setText(String.valueOf(winRate) + " %");
        unentschieden.setText(String.valueOf(sharedPrefs.getInt("Unentschieden", 0)));
        gesamtSpielZahl.setText(String.valueOf(allGames));
    }
}
