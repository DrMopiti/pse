package com.example.user.schachapp;

import android.os.AsyncTask;

public class NewGameTask extends AsyncTask<String, Integer, String> {


	@Override
	protected String doInBackground(String... strings) {
		ClientSocket cs = new ClientSocket();
		return cs.newGame(strings[0], strings[1]);
	}

}
