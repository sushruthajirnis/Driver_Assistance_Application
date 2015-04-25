package com.sjsu.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sjsu.model._User;
import com.sjsu.utility.DatabaseHandler;
import com.sjsu.utility.UserFunctions;

public class Register extends Activity {
	/**
	 * Defining layout items.
	 **/
	
	EditText inputFirstName;
	EditText inputUsername;
	EditText inputPassword;
	RadioGroup radiogroupRole;
	RadioButton radButton;
	Button btnRegister;
	TextView registerErrorMsg;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		
        		/**
		 * Defining all layout items
		 **/
		inputFirstName = (EditText) findViewById(R.id.fname);
		inputUsername = (EditText) findViewById(R.id.uname);
		inputPassword = (EditText) findViewById(R.id.pword);
		btnRegister = (Button) findViewById(R.id.register);
		radiogroupRole = (RadioGroup) findViewById(R.id.radioRole);
		registerErrorMsg = (TextView) findViewById(R.id.register_error);

		/**
		 * Button which Switches back to the login screen on clicked
		 **/
		
		Button login = (Button) findViewById(R.id.bktologin);
		login.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent myIntent = new Intent(view.getContext(), Login.class);
				startActivityForResult(myIntent, 0);
				finish();
			}
		});
		
		
		/**
		 * Register Button click event. A Toast is set to alert when the fields
		 * are empty. Another toast is set to alert Username must be 5
		 * characters.
		 **/
		
				btnRegister.setOnClickListener(new View.OnClickListener() {

			
					@Override
			public void onClick(View view) {
						String inputRole ;
				        int selectedId = radiogroupRole.getCheckedRadioButtonId();
				        radButton = (RadioButton) findViewById(selectedId);
				        inputRole = radButton.getText().toString();
						if(inputRole == "Company") inputRole = "2";
						else inputRole = "1";
						
				if ((!inputUsername.getText().toString().equals(""))
						&& (!inputPassword.getText().toString().equals(""))
						&& (!inputFirstName.getText().toString().equals(""))
						&& (!inputRole.equals(""))) {
					if (inputUsername.getText().toString().length() > 4) {
						NetAsync(view);
					} else {
						Toast.makeText(getApplicationContext(),
								"Username should be minimum 5 characters",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(getApplicationContext(),
							"One or more fields are empty", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});
	}
	/**
	 * Async Task to check whether internet connection is working
	 **/

	private class NetCheck extends AsyncTask<String, String, Boolean> {
		private ProgressDialog nDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			nDialog = new ProgressDialog(Register.this);
			nDialog.setMessage("Loading..");
			nDialog.setTitle("Checking Network");
			nDialog.setIndeterminate(false);
			nDialog.setCancelable(true);
			nDialog.show();
		}

		@Override
		protected Boolean doInBackground(String... args) {

			/**
			 * Gets current device state and checks for working internet
			 * connection by trying Google.
			 **/
			ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo netInfo = cm.getActiveNetworkInfo();
			if (netInfo != null && netInfo.isConnected()) {
				try {
					URL url = new URL("http://www.google.com");
					HttpURLConnection urlc = (HttpURLConnection) url
							.openConnection();
					urlc.setConnectTimeout(3000);
					urlc.connect();
					if (urlc.getResponseCode() == 200) {
						return true;
					}
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return false;

		}

		@Override
		protected void onPostExecute(Boolean th) {

			if (th == true) {
				nDialog.dismiss();
				new ProcessRegister().execute();
			} else {
				nDialog.dismiss();
				registerErrorMsg.setText("Error in Network Connection");
			}
		}
	}

	private class ProcessRegister extends AsyncTask<String, String, _User> {

		/**
		 * Defining Process dialog
		 **/
		private ProgressDialog pDialog;

		String password, fname, uname,role;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
	        int selectedId = radiogroupRole.getCheckedRadioButtonId();
	        radButton = (RadioButton) findViewById(selectedId);
	        role = radButton.getText().toString();
			if(role == "Company") 
				role = "2";
			else 
				role = "1";
			System.out.println("My Role: "+role);
			fname = inputFirstName.getText().toString();
			uname = inputUsername.getText().toString();
			password = inputPassword.getText().toString();
			pDialog = new ProgressDialog(Register.this);
			pDialog.setTitle("Contacting Servers");
			pDialog.setMessage("Registering ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected _User doInBackground(String... args) {
			UserFunctions userFunction = new UserFunctions();
			JSONObject json = userFunction.registerUser(fname, uname, password,role);
			System.out.println(json);
			Gson gson = new Gson();
			return gson.fromJson(json.toString(), _User.class);

		}

		@Override
		protected void onPostExecute(_User user) {
			/**
			 * Checks for success message.
			 **/
			try {
				if (user != null)
					if (user.getName() != null) {
						registerErrorMsg.setText("Successfully Registered");
						DatabaseHandler db = new DatabaseHandler(
								getApplicationContext());

						/**
						 * Removes all the previous data in the SQlite database
						 **/

						UserFunctions logout = new UserFunctions();
						logout.logoutUser(getApplicationContext());
						db.addUser(user.getName(), user.getRole().toString(),
								user.getUserName(), user.getId().toString());
						/**
						 * Stores registered data in SQlite Database Launch
						 * Registered screen
						 **/

						Intent registered = new Intent(getApplicationContext(),
								Registered.class);

						/**
						 * Close all views before launching Registered screen
						 **/
						registered.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						pDialog.dismiss();
						startActivity(registered);

						finish();
					} else {
						pDialog.dismiss();

						registerErrorMsg
								.setText("Error occured in registration");
					}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void NetAsync(View view) {
		new NetCheck().execute();
	}
}
