package helmig.daniel.test;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity {
	static Context applicationContext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		applicationContext = this.getApplicationContext();
		setContentView(R.layout.activity_main);
		startService(new Intent(this, WebsocketService.class));

	}

	public static Context getContext() {
		return applicationContext;
	}
}

