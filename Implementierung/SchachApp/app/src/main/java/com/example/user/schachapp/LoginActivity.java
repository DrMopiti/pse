package com.example.user.schachapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    EditText userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button login = findViewById(R.id.login_button);
        userName = findViewById(R.id.userName);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginClicked();
            }
        });
    }

    public void loginClicked() {
        if (userName.getText().length() > 0) {
            Intent intent = new Intent(this, MainMenuActivity.class);
            startActivity(intent);
            SharedPreferences sharedPrefs = getSharedPreferences("chessApp", 0);
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putString("Username", userName.getText().toString());
            editor.commit();
        }
    }
}