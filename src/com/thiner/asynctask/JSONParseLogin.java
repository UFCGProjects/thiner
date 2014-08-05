package com.thiner.asynctask;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;

public class JSONParseLogin extends AsyncTask<String, String, JSONObject> {


	public static final String TAG_ID = "id";
	public static final String PREF_CONNECTED = "pref_connected";

	public static String userId;
	Activity mActivity;
	List<NameValuePair> nameValuePairs;
	String url = "http://192.168.2.25:3000/login";
	JSONArray android = null;

	public JSONParseLogin(Activity acticity, String login, String senha) {
		nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("email", login));
		nameValuePairs.add(new BasicNameValuePair("senha", senha));
		userId = null;

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
	protected void onPostExecute(JSONObject result) {

	}
}
