package com.sjsu.service;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;

import com.yelp.parcelgen.JsonParser.DualCreator;


public class Business extends _Business {

	public static final DualCreator<Business> CREATOR = new DualCreator<Business>() {

		public Business[] newArray(int size) {
			return new Business[size];
		}

		public Business createFromParcel(Parcel source) {
			Business object = new Business();
			object.readFromParcel(source);
			return object;
		}

		@Override
		public Business parse(JSONObject obj) throws JSONException {
			Business newInstance = new Business();
			newInstance.readFromJson(obj);
			return newInstance;
		}
	};

	@Override
	public String toString() {
		return getName();
	}
}
