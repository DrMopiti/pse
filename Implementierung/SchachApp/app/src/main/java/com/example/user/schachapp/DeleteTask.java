package com.example.user.schachapp;

import android.os.AsyncTask;

public class DeleteTask extends AsyncTask<String, Integer, String> {


	@Override
	protected String doInBackground(String... strings) {
		ClientSocket cs = new ClientSocket(strings[0]);
		return cs.delete(strings[0]);
	}

}
