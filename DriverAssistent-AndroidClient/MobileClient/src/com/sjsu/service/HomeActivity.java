package com.sjsu.service;

import java.util.HashMap;

import com.sjsu.utility.DatabaseHandler;

import android.os.Bundle;
import android.widget.TextView;


public class HomeActivity extends DashboardActivity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		DatabaseHandler db = new DatabaseHandler(getApplicationContext());

        /**
         * Hashmap to load data from the Sqlite database
         **/
         HashMap<String,String> user = new HashMap<String, String>();
         user = db.getUserDetails();
		TextView name = (TextView) findViewById(R.id.welcme);
        name.setDrawingCacheBackgroundColor(7);
		name.setText("Welcome  "+user.get("fname"));
	}

	protected void onDestroy () {
		super.onDestroy ();
	}

	protected void onPause () {
		super.onPause ();
	}

	protected void onRestart () {
		super.onRestart ();
	}

	protected void onResume () {
		super.onResume ();
	}

	protected void onStart () {
		super.onStart ();
	}

	protected void onStop () {
		super.onStop ();
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
}