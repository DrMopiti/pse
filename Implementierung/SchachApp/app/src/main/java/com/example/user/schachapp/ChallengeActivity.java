package com.example.user.schachapp;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

/**
 * The type Challenge activity.
 * This Activity is to challenge other players.
 */
public class ChallengeActivity extends AppCompatActivity {
    private ClientSocket cs;
    private String challengedPlayer;
    private String user;
    private SharedPreferences sharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // stores different things.
        sharedPrefs = getSharedPreferences("chessApp", 0);
        setContentView(R.layout.activity_challenge);
        TextView username = findViewById(R.id.userName);
        TextView status = findViewById(R.id.status);


        //get data from previous activity when item of listview is clicked using intent.
        Intent intent = getIntent();
        challengedPlayer = intent.getStringExtra("clickedPlayer");
        user = sharedPrefs.getString("Username", "NoUser");
        username.setText(challengedPlayer);
        Boolean online;
        try {
            online = new IsOnlineTask().execute(challengedPlayer).get();

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            online = false;          
        }

        Button buttonChallenge = findViewById(R.id.challengeButton);
        if (online) {
            status.setText("online");
        } else {
            buttonChallenge.setEnabled(false);
            status.setText("offline");
        }



        // clickListener for challenging other player
        buttonChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                challengeClicked();
            }
        });
    }

    /**
     * Challenge is clickable, when player is online.
     * When the player is online, it changes in the BoardActivity and it counts allGames.
     * When the player is offline, the button is not clickable.
     */
    public void challengeClicked() {
        try {
          //  View view = findViewById(com.example.user.schachapp.R.id.ChallengeActivity);
            if (new IsOnlineTask().execute(challengedPlayer).get()) {
                if (new HasGameTask().execute(challengedPlayer).get()) {
                    //Snackbar.make(view,"Spieler spielt bereits", Snackbar.LENGTH_INDEFINITE).show();
                    Toast.makeText(this, "Spieler spielt bereits!", Toast.LENGTH_LONG).show();
                } else if (new HasGameTask().execute(user).get()) {
                   // Snackbar.make(view,"Sie spielen bereits", Snackbar.LENGTH_INDEFINITE).show();
                    Toast.makeText(this, "Sie spielen bereits!", Toast.LENGTH_LONG).show();

                } else {
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    int allGames = sharedPrefs.getInt("GesamtSpielAnzahl", 0);
                    allGames++;
                    editor.putInt("GesamtSpielAnzahl", allGames);
                    editor.apply();
                    SharedPreferences sharedPrefs = getSharedPreferences("chessApp", 0);
                    String user = sharedPrefs.getString("Username", "");
                    String success = new NewGameTask().execute(user, challengedPlayer).get();
                    System.out.println("create:   " + success);
                    Intent intent = new Intent(this, BoardActivity.class);
                    intent.putExtra("isOnlineGame", true);
                    startActivity(intent);
                }
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

