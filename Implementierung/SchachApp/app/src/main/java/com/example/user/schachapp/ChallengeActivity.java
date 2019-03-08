package com.example.user.schachapp;
import android.content.Intent;
import android.content.SharedPreferences;
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
    private SharedPreferences sharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // stores different things.
        sharedPrefs = getSharedPreferences("chessApp", 0);
        setContentView(R.layout.activity_challenge);
        TextView username = findViewById(R.id.userName);
        TextView status = findViewById(R.id.status);
        String user = sharedPrefs.getString("Username", "");
        cs = new ClientSocket(user);

        //get data from previous activity when item of listview is clicked using intent.
        Intent intent = getIntent();
        challengedPlayer = intent.getStringExtra("clickedPlayer");
        username.setText(challengedPlayer);
        Boolean online;
        try {
            online = new IsOnlineTask().execute(challengedPlayer).get();
           // Log.d("1adasdasd","111111sad");
			//Toast.makeText(this, "111111" + "   " + online.toString(), Toast.LENGTH_LONG).show();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            online = false;
          //  Log.d("22222aasdasd","222222sad");
          //  Toast.makeText(this, "22222", Toast.LENGTH_LONG).show();;
        }
        if (online) {
            status.setText("online");
        } else {
            status.setText("offline");
        }

        Button buttonChallenge = findViewById(R.id.challengeButton);

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
            if (new IsOnlineTask().execute(challengedPlayer).get()) {
                SharedPreferences.Editor editor = sharedPrefs.edit();
                int allGames = sharedPrefs.getInt("GesamtSpielAnzahl", 0);
                allGames++;
                editor.putInt("GesamtSpielAnzahl", allGames);
                editor.apply();
                SharedPreferences sharedPrefs = getSharedPreferences("chessApp", 0);
                String user = sharedPrefs.getString("Username", "");
                new NewGameTask().execute(user, challengedPlayer);
               /* ThreadHandler th = new ThreadHandler();
                th.runInBackground(new Runnable() {
                    @Override
                    public void run() {
                        SharedPreferences sharedPrefs = getSharedPreferences("chessApp", 0);
                        String hi = cs.newGame(sharedPrefs.getString("Username", ""), challengedPlayer);
                    }
                });*/

                Intent intent = new Intent(this, BoardActivity.class);
                intent.putExtra("isOnlineGame", true);
                startActivity(intent);
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

