package com.sjsu.service;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sjsu.yelp.Yelp;
import com.yelp.parcelgen.JsonUtil;

public abstract class DashboardActivity extends Activity implements LocationListener{
	
	public static final int DIALOG_PROGRESS = 42;
	
	GPSTracker gps;
	private Yelp mYelp;
	private YelpAuth y;
	String longitude, latitude;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		y = new YelpAuth();
		mYelp = new Yelp(y.getYelpConsumerKey(), y.getYelpConsumerSecret(),
				y.getYelpToken(), y.getYelpTokenSecret());
	}

	@Override
    public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
    }
	
	protected void onDestroy() {
		super.onDestroy();
	}

	protected void onPause() {
		super.onPause();
	}

	protected void onRestart() {
		super.onRestart();
	}

	protected void onResume() {
		super.onResume();
	}

	protected void onStart() {
		super.onStart();
	}

	protected void onStop() {
		super.onStop();
	}

	public void onClickHome(View v) {
		goHome(this);
	}

	public void onClickSearch(View v) {
		goSearch(this);
	}

	public void onClickAbout(View v) {
		goAbout(this);
	}

	public void onClickFeature(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.home_btn_feature1:
			System.out.println(v.getContext().getString(id));
			search(v.getContext().getString(R.string.description_feature1));
			break;
		case R.id.home_btn_feature2:
			search(v.getContext().getString(R.string.description_feature2));
			break;
		case R.id.home_btn_feature3:
			search(v.getContext().getString(R.string.description_feature3));
			break;
		case R.id.home_btn_feature4:
			search(v.getContext().getString(R.string.description_feature4));
			break;
		case R.id.home_btn_feature5:
			search(v.getContext().getString(R.string.description_feature5));
			break;
		case R.id.home_btn_feature8:
			search(v.getContext().getString(R.string.description_feature8));
			break;
		case R.id.home_btn_feature10:
			search(v.getContext().getString(R.string.description_feature10));
			break;
		case R.id.home_btn_feature12:
			search(v.getContext().getString(R.string.description_feature12));
			break;
		default:
			break;
		}

	}

	@SuppressWarnings("deprecation")
	public void search(String terms) {
		gps = new GPSTracker(DashboardActivity.this);
		 if(gps.canGetLocation()){
             latitude = String.valueOf(gps.getLatitude());
             longitude = String.valueOf(gps.getLongitude());
		 }else
			 gps.showSettingsAlert();
		String message = String.format(
				"Current Location \n Longitude: %1$s \n Latitude: %2$s",
				longitude, latitude);
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
		showDialog(DIALOG_PROGRESS);
		new AsyncTask<String, Void, ArrayList<Business>>() {
			@Override
			protected ArrayList<Business> doInBackground(String... params) {
				String result = mYelp.search(params[0],
						Double.parseDouble(params[1]),
						Double.parseDouble(params[2]));
				System.out.println("Result" + result);
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

	@SuppressWarnings("deprecation")
	public void onSuccess(ArrayList<Business> businesses) {
		dismissDialog(DIALOG_PROGRESS);
		if (businesses != null) {
			Intent intent = new Intent(DashboardActivity.this,
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
	public void onLocationChanged(Location location) {
		
		location.getLatitude();
		location.getLongitude();
		String myLocation = "Latitude = " + location.getLatitude()
				+ " Longitude = " + location.getLongitude();
		System.out.println("Location is " + myLocation);
		Log.d("MY CURRENT LOCATION", myLocation);
	}

	public void goSearch(Context context) {
		final Intent intent = new Intent(context, SearchActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(intent);
	}

	public void goAbout(Context context) {
		final Intent intent = new Intent(context, AboutActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(intent);
	}

	public void goHome(Context context) {
		final Intent intent = new Intent(context, HomeActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(intent);
	}

	public void setTitleFromActivityLabel(int textViewId) {
		TextView tv = (TextView) findViewById(textViewId);
		if (tv != null)
			tv.setText(getTitle());
	}

	public void toast(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	}


}
