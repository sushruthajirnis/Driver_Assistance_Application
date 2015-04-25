package com.sjsu.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JSONParser {

	static InputStream is = null;
	static JSONObject jObj = null;
	static String json = "";

	// constructor
	public JSONParser() {

	}

	public JSONObject getJSONFromUrl(String type, String url,
			List<NameValuePair> params) {

		// Making HTTP request
		try {
			HttpResponse httpResponse;
			// defaultHttpClient
			DefaultHttpClient httpClient = new DefaultHttpClient();
			if (type.equals("post")) {
				HttpPost httpPost = new HttpPost(url);
				httpPost.setEntity(new UrlEncodedFormEntity(params));
				httpResponse = httpClient.execute(httpPost);
				System.out.println("Post Action: " + httpPost.getURI());
			} else {
				HttpGet httpGet = new HttpGet(url);
				
				httpResponse = httpClient.execute(httpGet);
				System.out.println("Get Action: " + httpGet.getURI());
				
			}
			HttpEntity httpEntity = httpResponse.getEntity();
			System.out.println("Entity: "+httpEntity);
			is = httpEntity.getContent();
			System.out.println("is: "+is);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			System.out.println("sb: "+sb);
			json = sb.toString();
			Log.e("JSON", json);
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}
		// try parse the string to a JSON object
		try {
			jObj = new JSONObject(json);
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}
		// return JSON String
		return jObj;
	}
}