package com.thiner.screen.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.thiner.R;
import com.thiner.adapter.PersonAdapter;
import com.thiner.asynctask.GetJSONTask;
import com.thiner.asynctask.GetJSONTask.GetJSONInterface;
import com.thiner.model.Person;
import com.thiner.utils.MyLog;

public class SearchActivity extends Activity implements GetJSONInterface {

    private Button mBtGoSearch;
    private EditText mSearchText;
    private ListView mList;
    private ProgressBar mProgress;
    private ArrayList<Person> mListContacts;
    private PersonAdapter mContactAdapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Get Refferences of Views

        // edConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);

        mBtGoSearch = (Button) findViewById(R.id.buttonCreateAccount);
        mBtGoSearch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                sendGetUserInformation();

            }

        });
    }

    private void sendGetUserInformation() {
        mSearchText = (EditText) findViewById(R.id.TextViewSearch);

        final String username = "username=" + mSearchText.getText().toString();
        new GetJSONTask(this).execute(username);
    }

    private void updatePersons(final List<Person> array) {
        if (mListContacts != null && mContactAdapter != null) {
            mListContacts.clear();

            mListContacts.addAll(array);

            Collections.sort(mListContacts);

            mContactAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void callbackDownloadJSON(final JSONObject json) {
        final JSONArray users;

        final List<Person> array = new ArrayList<Person>();

        try {

            users = json.getJSONArray("users");
            for (int i = 0; i < users.length(); i++) {
                MyLog.info(users.getJSONObject(i).toString());

                JSONObject user;
                user = users.getJSONObject(i);

                final String firstName = user.getString("firstname");
                final String secondName = user.getString("lastname");
                final String username = user.getString("username");
                final String email = user.getString("lastname");
                final String operadora = user.getString("operadora");

                final Person newPerson = new Person(firstName, secondName, username, email,
                        operadora);
                array.add(newPerson);
            }

            updatePersons(array);

        } catch (final JSONException e) {
            e.printStackTrace();
        }

        mProgress.setVisibility(View.GONE);
        mList.setVisibility(View.VISIBLE);
    }
}
