package com.example.user.schachapp;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class StatisticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPrefs = getSharedPreferences("chessApp", 0);
        setContentView(com.example.user.schachapp.R.layout.activity_statistics);
        TextView username = findViewById(R.id.usernameText);
        TextView gewonnen = findViewById(R.id.gewonnenZahl);
        TextView verloren = findViewById(R.id.verlorenZahl);
        TextView gewinnrate = findViewById(R.id.gewinnrateZahl);
        TextView unentschieden = findViewById(R.id.unentschiedenZahl);
        TextView gesamtSpielZahl = findViewById(R.id.gesamtspielanzahlZahl);

        username.setText(sharedPrefs.getString("Username", ""));
        String wonGames = sharedPrefs.getString("Gewonnen", "0");
        String allGames = sharedPrefs.getString("GesamtSpielAnzahl", "0");
        gewonnen.setText(wonGames);
        verloren.setText(sharedPrefs.getString("Verloren", "0"));
        float won = Integer.valueOf(wonGames);
        float all = Integer.valueOf(allGames);
        float winRate = Math.round((won / all) * 100);
        winRate = winRate / 100;
        gewinnrate.setText(String.valueOf(winRate) + " %");
        unentschieden.setText(sharedPrefs.getString("Unentschieden", "0"));
        gesamtSpielZahl.setText(allGames);
    }
}
