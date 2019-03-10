package com.example.user.schachapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * The type Login activity.
 * This Activity opens a LoginActivity where the player can set his user name for the hole app.
 * After setting his user name, the player stays logged in all the time.
 */
public class LoginActivity extends AppCompatActivity {

    private EditText userName;

    private Set<String> userSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            userSet = new GetPlayersTask().execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            userSet = Collections.emptySet();

        }
        setContentView(R.layout.activity_login);
        Button login = findViewById(R.id.login_button);
        userName = findViewById(R.id.userName);
        userName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if (userSet.contains(s.toString())) {
                    userName.setError("Nutzer existiert bereits!");
                }
            }
        });
        userName.setError("Name fehlt");

        // clickListenerto sign in and change to the MainMenuActivity
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginClicked();
            }
        });
    }

    /**
     * When Login is clicked the player goes into the MainMenuActivity. The username stays stored local.
     */
    public void loginClicked() {
        if (userName.getError() != null) {
            Toast.makeText(this, "Ungültiger Name", Toast.LENGTH_LONG).show();
        } else {
            String user = userName.getText().toString();
            if ((user.length() > 0) && (user.length() <= 30)) {
                Intent intent = new Intent(this, MainMenuActivity.class);
                startActivity(intent);
                SharedPreferences sharedPrefs = getSharedPreferences("chessApp", 0);
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString("Username", userName.getText().toString());
                editor.commit();
            }
        }
    }

    @Override
    public void onBackPressed() {

    }
}