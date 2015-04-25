package com.sjsu.service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sjsu.yelp.Yelp;
import com.yelp.parcelgen.JsonUtil;

public class SearchActivity extends DashboardActivity implements
		LocationListener {
	public static final int DIALOG_PROGRESS = 42;
	private static final int VOICE_RECOGNITION_REQUEST_CODE = 1001;
	GPSTracker gps;
	private Yelp mYelp;

	String longitude, latitude;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		YelpAuth y = new YelpAuth();
		mYelp = new Yelp(y.getYelpConsumerKey(), y.getYelpConsumerSecret(),
				y.getYelpToken(), y.getYelpTokenSecret());
		checkVoiceRecognition();
	}

	public void onLocationChanged(Location location) {
		location.getLatitude();
		location.getLongitude();
		String myLocation = "Latitude = " + location.getLatitude()
				+ " Longitude = " + location.getLongitude();
		System.out.println("Location is " + myLocation);
		Log.e("MY CURRENT LOCATION", myLocation);
	}

	public void checkVoiceRecognition() {
		// Check if voice recognition is present
		PackageManager pm = getPackageManager();
		List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(
				RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
		if (activities.size() == 0) {
			Toast.makeText(this, "Voice recognizer not present",
					Toast.LENGTH_SHORT).show();
		}
	}

	public void speak(View view) {
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getClass()
				.getPackage().getName());
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
		startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == VOICE_RECOGNITION_REQUEST_CODE)
			if (resultCode == RESULT_OK) {
				ArrayList<String> textMatchList = data
						.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
				if (!textMatchList.isEmpty()) {
					((EditText) findViewById(R.id.searchTerm))
							.setText(textMatchList.get(0));
				}
			}
	}

	public void search(View v) {
		String terms = ((EditText) findViewById(R.id.searchTerm)).getText()
				.toString();
		gps = new GPSTracker(SearchActivity.this);
		if (gps.canGetLocation()) {
			latitude = String.valueOf(gps.getLatitude());
			longitude = String.valueOf(gps.getLongitude());
		} else
			gps.showSettingsAlert();
		String message = String.format(
				"Current Location \n Longitude: %1$s \n Latitude: %2$s",
				longitude, latitude);
		Toast.makeText(SearchActivity.this, message, Toast.LENGTH_LONG).show();
		showDialog(DIALOG_PROGRESS);
		new AsyncTask<String, Void, ArrayList<Business>>() {

			@Override
			protected ArrayList<Business> doInBackground(String... params) {
				String result = mYelp.search(params[0],
						Double.parseDouble(params[1]),
						Double.parseDouble(params[2]));
				System.out.println(result);
				try {
					JSONObject response = new JSONObject(result);
					if (response.has("businesses")) {
						return JsonUtil.parseJsonList(
								response.getJSONArray("businesses"),
								Business.CREATOR);
					}
				} catch (JSONException e) {
					return null;
				}
				return null;
			}

			@Override
			protected void onPostExecute(ArrayList<Business> businesses) {
				onSuccess(businesses);
			}

		}.execute(terms, latitude, longitude);
	}

	public void onSuccess(ArrayList<Business> businesses) {
		dismissDialog(DIALOG_PROGRESS);
		if (businesses != null) {
			Intent intent = new Intent(SearchActivity.this,
					BusinessesActivity.class);
			intent.putParcelableArrayListExtra(
					BusinessesActivity.EXTRA_BUSINESSES, businesses);
			startActivity(intent);
		} else {
			Toast.makeText(this, "An error occured during search",
					Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public Dialog onCreateDialog(int id) {
		if (id == DIALOG_PROGRESS) {
			ProgressDialog dialog = new ProgressDialog(this);
			dialog.setMessage("Loading...");
			return dialog;
		} else {
			return null;
		}
	}

	@Override
	public void onProviderDisabled(String arg0) {
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