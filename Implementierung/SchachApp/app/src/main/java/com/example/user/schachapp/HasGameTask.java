package com.example.user.schachapp;

import android.os.AsyncTask;

public class HasGameTask extends AsyncTask<String, Integer, Boolean> {


	@Override
	protected Boolean doInBackground(String... strings) {
		ClientSocket cs = new ClientSocket();
		return cs.hasGame(strings[0]);
	}
}
