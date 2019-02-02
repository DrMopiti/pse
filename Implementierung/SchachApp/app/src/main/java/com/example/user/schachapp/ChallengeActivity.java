package com.example.user.schachapp;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ChallengeActivity extends AppCompatActivity {
    private ClientSocket cs;
    private String challengedPlayer;
    private SharedPreferences sharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPrefs = getSharedPreferences("chessApp", 0);
        setContentView(R.layout.activity_challenge);
        TextView username = findViewById(R.id.userName);
        TextView status = findViewById(R.id.status);
        String user = sharedPrefs.getString("Username", "");
        cs = new ClientSocket(user);

        //get data from previous activity when item of listview is clicked using intent
        Intent intent = getIntent();
        challengedPlayer = intent.getStringExtra("clickedPlayer");
        username.setText(challengedPlayer);
        if (/*cs.isOnline(challengedPlayer)*/true) {
            status.setText("online");
        } else {
            status.setText("offline");
        }

        Button buttonChallenge = findViewById(R.id.challengeButton);

        buttonChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                challengeClicked();
            }
        });
    }

    public void challengeClicked() {
        if (/*cs.isOnline(challengedPlayer)*/true) {
            SharedPreferences.Editor editor = sharedPrefs.edit();
            int allGames = Integer.valueOf(sharedPrefs.getString("GesamtSpielAnzahl", "0"));
            allGames++;
            editor.putString("GesamtSpielAnzahl", String.valueOf(allGames));
            editor.commit();
            //cs.newGame(sharedPrefs.getString("Username", ""), challengedPlayer);
            Intent intent = new Intent(this, BoardActivity.class);
            startActivity(intent);
        }
    }
}

