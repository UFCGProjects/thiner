package com.thiner.asynctask;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.thiner.R;

public class JSONParserSignUP extends AsyncTask<String, String, JSONObject> {
	JSONArray android = null;
	List<NameValuePair> nameValuePairs;

	public JSONParserSignUP(Activity acticity, String username,
			String firstname, String lastname, String password, String email) {
		nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("username", username));
		nameValuePairs.add(new BasicNameValuePair("firstname", firstname));
		nameValuePairs.add(new BasicNameValuePair("lastname", lastname));
		nameValuePairs.add(new BasicNameValuePair("password", password));
		nameValuePairs.add(new BasicNameValuePair("email", email));
		;

	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

	}

	@Override
	protected JSONObject doInBackground(String... params) {
		JSONParser json = new JSONParser();
		JSONObject jsonObject = json.postData("http://192.168.2.25:3000/login",
				nameValuePairs);
		return jsonObject;
	}

	@Override
	protected void onPostExecute(JSONObject json) {

	}

}
