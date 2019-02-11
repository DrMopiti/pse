package com.example.user.schachapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * The type Lost activity.
 * This Activity opens a LostActivity when the player loses a game.
 */
public class LostActivity extends AppCompatActivity {

    private Button menuButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost);

        menuButton = findViewById(R.id.menü);

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
}
