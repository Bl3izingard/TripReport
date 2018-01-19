package fr.suid.tripreport2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.os.Handler;

public class MainActivity extends AppCompatActivity
{

	private static int TripReport_TIME_OUT = 2000;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//Passage à la vue suivante pas de retour possible
		Intent intent = new Intent(MainActivity.this, HomeActivity.class);

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
				startActivity(homeIntent);
				finish();
			}
		}, TripReport_TIME_OUT);
	}


}
