package com.sjsu.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class BusinessActivity extends Activity implements LocationListener{

	public static final String EXTRA_BUSINESS = "business";
	public static final String EXTRA_BUSINESSES = "businesses";
	private double lng = 0,lat = 0;
	GPSTracker gps;
	ImageView img;
	ImageView rating;
	
	private String longitude, latitude;
	
	static String add = null;
	static String dist = null;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.business);
		gps = new GPSTracker(BusinessActivity.this);
		 if(gps.canGetLocation()){
            latitude = String.valueOf(gps.getLatitude());
            longitude = String.valueOf(gps.getLongitude());
		 }else
			 gps.showSettingsAlert();
		Business business = getIntent().getParcelableExtra(EXTRA_BUSINESS);
		setTitle(business.getName());
		//Fetch Name
		String name = business.getName();

		//Fetch the Address
		String address = business.mLocation.mDisplayAddress.toString();
		add = address;
		
		//Fetch Distance
		double distance = (double)(Math.round((business.getDistance() * 0.000621371)*100.0))/100.0;
		dist = String.valueOf(distance);
		dist = dist+" miles";
		//Fetch Review of place
		String review = business.getSnippetText();
		String phone;
		try{
			phone = business.getPhone();        	
		}
		catch(Exception ex)
		{
			phone = "Not Present";
		}
		int loader = R.drawable.airport_default;
		img = (ImageView)findViewById(R.id.imagenew);
		String imageurl;
		try{
			imageurl = business.getImageUrl();        	
		}
		catch(Exception ex)
		{
			imageurl = "Not Present";
		}
		ImageLoader imgLoader = new ImageLoader(getApplicationContext());
		imgLoader.DisplayImage(imageurl, loader, img);
		
		int ratingloader = R.drawable.airport_default;
		
		rating = (ImageView)findViewById(R.id.ratingimagenew);
		String ratingimageurl;
		try{
			ratingimageurl = business.getRatingImageUrl();        	
		}
		catch(Exception ex)
		{
			ratingimageurl = "Not Present";
		}
		ImageLoader ratingImgLoader = new ImageLoader(getApplicationContext());
		ratingImgLoader.DisplayImage(ratingimageurl, ratingloader, rating);
		
		((TextView)findViewById(R.id.name1)).setText(name);
		((TextView)findViewById(R.id.address1)).setText(address);
		((TextView)findViewById(R.id.phone1)).setText(phone);
		((TextView)findViewById(R.id.dist)).setText(dist);
		((TextView)findViewById(R.id.review1)).setText(review);
	}
	
	public void onLocationChanged(Location location) {
		location.getLatitude();
		location.getLongitude();
		String myLocation = "Latitude = " + location.getLatitude()
				+ " Longitude = " + location.getLongitude();
		System.out.println("Location is " + myLocation);
		Log.d("MY CURRENT LOCATION", myLocation);
	}

	@Override
	public void onStatusChanged(String s, int i, Bundle bundle) {
	}

	@Override
	public void onProviderEnabled(String s) {
	}

	@Override
	public void onProviderDisabled(String s) {
	}
	protected Bitmap getImage(String urldisplay ) {
		Bitmap mIcon11 = null;
		try {
			InputStream in = new java.net.URL(urldisplay).openStream();
			mIcon11 = BitmapFactory.decodeStream(in);
		} catch (Exception e) {
			Log.e("Error", e.getMessage());
			e.printStackTrace();
		}
		return mIcon11;
	}
	public static Drawable dispImage(String url) {
		try {
			InputStream is = (InputStream) new URL(url).getContent();
			System.out.println("Inside stream :"+is);
			Drawable d = Drawable.createFromStream(is, "image");
			System.out.println("Inside drawable : "+d);
			return d;
		} 
		catch (Exception e) {
			return null;
		}
	}
	public void getDirection(View v){
		goMap(this);
	}
	@SuppressWarnings("static-access")
	@SuppressLint("NewApi")
	public void goMap(Context context)  {	
		Geocoder gc = new Geocoder(context);
		if(gc.isPresent()){
			 List<Address> list = new ArrayList<Address>();
			try {
				list = gc.getFromLocationName(add, 1);
			} catch (IOException e) {
				e.printStackTrace();
			}
			 Address address = list.get(0);
			  lat = address.getLatitude();
			  lng = address.getLongitude();
		}
		Bundle extras = new Bundle();
		extras.putString("long", String.valueOf(lng));
		extras.putString("lat", String.valueOf(lat));
		extras.putString("mylong", longitude);
		extras.putString("mylat", latitude);
		System.out.println("My Long: "+Double.parseDouble(longitude)+" My Lat: "+Double.parseDouble(latitude) +" add long: "+lng+" add lat: "+lat );
		Intent intent = new Intent(context, com.sjsu.service.DirectionsExample.class);
		intent.putExtras(extras);
		startActivity(intent);
	}
}