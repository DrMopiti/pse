package com.example.user.schachapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * The type Winner activity.
 * This Activity opens a WinnerActivity when the player wins a game.
 */
public class WinnerActivity extends AppCompatActivity {

    private Button menuButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner);

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
