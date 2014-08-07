
package com.thiner.screen.contact;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.thiner.R;
import com.thiner.adapter.PersonAdapter;
import com.thiner.asynctask.GetJSONTask;
import com.thiner.asynctask.GetJSONTask.GetJSONInterface;
import com.thiner.model.Person;
import com.thiner.utils.APIUtils;
import com.thiner.utils.AuthPreferences;
import com.thiner.utils.MyLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The Class MainActivity.
 */
public final class PersonActivity extends Activity implements GetJSONInterface {

    private ArrayList<Person> mListPersons;
    private PersonAdapter mPersonAdapter;
    private ListView mList;
    private ProgressBar mProgress;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        mListPersons = new ArrayList<Person>();

        mPersonAdapter = new PersonAdapter(this, R.layout.adapter_contact, mListPersons);

        mList = (ListView) findViewById(android.R.id.list);

        mList.setAdapter(mPersonAdapter);
        mList.setEmptyView(findViewById(android.R.id.empty));

        mProgress = (ProgressBar) findViewById(R.id.progressBar);

    }

    @Override
    protected void onResume() {
        super.onResume();

        mList.setVisibility(View.GONE);
        mProgress.setVisibility(View.VISIBLE);

        final String url = APIUtils.getApiUrl() + "?"
                + APIUtils.putAttrs("id", AuthPreferences.getID(this));

        new GetJSONTask(this).execute(url);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();// Close The DatabaseloginDataBaseAdapter.close();
    }

    public void updateTi(final List<Person> array) {
        if (mListPersons != null && mPersonAdapter != null) {
            mListPersons.clear();

            mListPersons.addAll(array);

            Collections.sort(mListPersons);

            mPersonAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void callbackDownloadJSON(final JSONObject json) {
        MyLog.warning(json.toString());

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

            updateTi(array);

        } catch (final JSONException e) {
            e.printStackTrace();
        }

        mProgress.setVisibility(View.GONE);
        mList.setVisibility(View.VISIBLE);
    }
}
