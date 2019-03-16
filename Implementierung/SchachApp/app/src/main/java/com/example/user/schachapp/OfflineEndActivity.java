package com.example.user.schachapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * The type Winner activity.
 * This Activity opens a WinnerActivity when the player wins a game.
 */
public class OfflineEndActivity extends AppCompatActivity {
    private TextView resultView;
    private Button menuButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        String result = intent.getStringExtra("result");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_end);
        resultView = findViewById(R.id.result);
        if (result.charAt(2) == '1') {
            resultView.setText("Schwarz gewinnt!");
        } else if (result.charAt(2) == '5') {
            resultView.setText("Unentschieden");
        } else if (result.charAt(2) == '0') {
            resultView.setText("Weiß gewinnt!");
        }
        menuButton = findViewById(R.id.menu);

        // clickListener to change to the MainMenuActivity.
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuClicked();
            }
        });
    }

    /**
     * When menu is clicked, the player goes back to the MainMenuActivity.
     */
    public void menuClicked() {
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

    }
}
