package com.thiner.screen.search;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.thiner.R;
import com.thiner.asynctask.GetJSONTask;
import com.thiner.asynctask.GetJSONTask.GetJSONInterface;

public class SearchActivity extends Activity implements GetJSONInterface {

	Button btGoSearch;
	EditText searchText;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		// Get Refferences of Views

		// edConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);

		btGoSearch = (Button) findViewById(R.id.buttonCreateAccount);
		btGoSearch.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(final View v) {
				sendGetUserInformation();

			}

		});
	}

	private void sendGetUserInformation() {
		searchText = (EditText) findViewById(R.id.TextViewSearch);

		final String username = "username=" + searchText.getText().toString();
		new GetJSONTask(this).execute(username);
	}

	@Override
	public void callbackDownloadJSON(final JSONObject json) {

	}

}
