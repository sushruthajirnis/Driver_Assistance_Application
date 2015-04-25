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
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sjsu.model._User;
import com.sjsu.utility.DatabaseHandler;
import com.sjsu.utility.UserFunctions;

public class Login extends Activity {

	Button btnLogin;
	Button Btnregister;
	Button passreset;
	EditText inputUsername;
	EditText inputPassword;
	private TextView loginErrorMsg;

	/**
	 * Called when the activity is first created.
	 */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);*/
		Settings.System.putString(getContentResolver(),
				Settings.System.HTTP_PROXY, "myproxy:8080");
		setContentView(R.layout.activity_login);

		inputUsername = (EditText) findViewById(R.id.uname);
		inputPassword = (EditText) findViewById(R.id.pword);
		Btnregister = (Button) findViewById(R.id.registerbtn);
		btnLogin = (Button) findViewById(R.id.login);
		passreset = (Button) findViewById(R.id.passres);
		loginErrorMsg = (TextView) findViewById(R.id.loginErrorMsg);

		passreset.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent myIntent = new Intent(view.getContext(),
						PasswordReset.class);
				startActivityForResult(myIntent, 0);
				finish();
			}
		});

		Btnregister.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent myIntent = new Intent(view.getContext(), Register.class);
				startActivityForResult(myIntent, 0);
				finish();
			}
		});

		/**
		 * Login button click event A Toast is set to alert when the Email and
		 * Password field is empty
		 **/
		btnLogin.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {

				if ((!inputUsername.getText().toString().equals(""))
						&& (!inputPassword.getText().toString().equals(""))) {
					NetAsync(view);
				} else if ((!inputUsername.getText().toString().equals(""))) {
					Toast.makeText(getApplicationContext(),
							"Password field empty", Toast.LENGTH_SHORT).show();
				} else if ((!inputPassword.getText().toString().equals(""))) {
					Toast.makeText(getApplicationContext(),
							"Email field empty", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getApplicationContext(),
							"Email and Password field are empty",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	/**
	 * Async Task to check whether internet connection is working.
	 **/
	private class NetCheck extends AsyncTask<String, String, Boolean> {
		private ProgressDialog nDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			nDialog = new ProgressDialog(Login.this);
			nDialog.setTitle("Checking Network");
			nDialog.setMessage("Loading..");
			nDialog.setIndeterminate(false);
			nDialog.setCancelable(true);
			nDialog.show();
		}

		/**
		 * Gets current device state and checks for working internet connection
		 * by trying Google.
		 **/
		@Override
		protected Boolean doInBackground(String... args) {

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
				new ProcessLogin().execute();
			} else {
				nDialog.dismiss();
				loginErrorMsg.setText("Error in Network Connection");
			}
		}
	}

	/**
	 * Async Task to get and send data to My Sql database through JSON respone.
	 **/
	private class ProcessLogin extends AsyncTask<String, String, _User> {

		private ProgressDialog pDialog;

		String uname, password;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			inputUsername = (EditText) findViewById(R.id.uname);
			inputPassword = (EditText) findViewById(R.id.pword);
			uname = inputUsername.getText().toString();
			password = inputPassword.getText().toString();
			pDialog = new ProgressDialog(Login.this);
			pDialog.setTitle("Contacting Servers");
			pDialog.setMessage("Logging in ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected _User doInBackground(String... args) {
			UserFunctions userFunction = new UserFunctions();
			JSONObject json = userFunction.loginUser(uname, password);
			Gson gson = new Gson();
			return gson.fromJson(json.toString(), _User.class);
		}

		@Override
		protected void onPostExecute(_User user) {
			try {
				if(user != null)
				if (user.getUserName() != null){
						pDialog.setMessage("Loading User Space");
						pDialog.setTitle("Getting Data");
						DatabaseHandler db = new DatabaseHandler(
								getApplicationContext());

						/**
						 * Clear all previous data in SQlite database.
						 **/
						UserFunctions logout = new UserFunctions();
						logout.logoutUser(getApplicationContext());
						db.addUser(user.getName(), user.getRole().toString(),
								user.getUserName(), user.getId().toString());
						/**
						 * If JSON array details are stored in SQlite it
						 * launches the User Panel.
						 **/
						Intent dash = new Intent(getApplicationContext(),
								HomeActivity.class);
						dash.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						pDialog.dismiss();
						startActivity(dash);
						/**
						 * Close Login Screen
						 **/
						finish();
					} else {

						pDialog.dismiss();
						loginErrorMsg.setText("Incorrect username/password");
					}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void NetAsync(View view) {
		new NetCheck().execute();
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

}