package com.example.user.schachapp;

import android.os.AsyncTask;

public class IsOnlineTask extends AsyncTask<String, Integer, Boolean> {


	@Override
	protected Boolean doInBackground(String... strings) {
		ClientSocket cs = new ClientSocket();
		return cs.isOnline(strings[0]);
	}

}
