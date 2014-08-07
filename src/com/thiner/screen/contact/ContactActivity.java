
package com.thiner.screen.contact;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.thiner.R;
import com.thiner.adapter.ContactAdapter;
import com.thiner.asynctask.GetJSONTask;
import com.thiner.asynctask.GetJSONTask.GetJSONInterface;
import com.thiner.model.Contact;
import com.thiner.screen.profile.ProfileActivity;
import com.thiner.screen.search.SearchActivity;
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
public final class ContactActivity extends Activity implements GetJSONInterface {

    private ArrayList<Contact> mListContacts;
    private ContactAdapter mContactAdapter;
    private ListView mList;
    private ProgressBar mProgress;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        mListContacts = new ArrayList<Contact>();

        mContactAdapter = new ContactAdapter(this, R.layout.adapter_contact, mListContacts);

        mList = (ListView) findViewById(android.R.id.list);

        mList.setAdapter(mContactAdapter);
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

    /*
     * (non-Javadoc)
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.search:
                final Intent intentSearch = new Intent(ContactActivity.this,
                        SearchActivity.class);
                startActivity(intentSearch);
                return true;
            case R.id.profile:
                final Intent intentProfile = new Intent(ContactActivity.this,
                        ProfileActivity.class);
                startActivity(intentProfile);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    /*
     * (non-Javadoc)
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.contact, menu);
        return true;
    }

    public void updateTi(final List<Contact> contacts) {
        if (mListContacts != null && mContactAdapter != null) {
            mListContacts.clear();

            mListContacts.addAll(contacts);

            Collections.sort(mListContacts);

            mContactAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void callbackDownloadJSON(final JSONObject json) {
        MyLog.warning(json.toString());

        try {
            final JSONArray friends = json.getJSONObject("users").getJSONArray("friends");

            final List<Contact> array = new ArrayList<Contact>();

            for (int i = 0; i < friends.length(); i++) {
                MyLog.info(friends.getJSONObject(i).toString());

                final JSONObject friend = friends.getJSONObject(i);

                final String firstName = friend.getString("firstname");
                final String secondName = friend.getString("lastname");
                final String operadora = "TIM";

                final Contact contact = new Contact(firstName, secondName, operadora);

                array.add(contact);
            }

            updateTi(array);

        } catch (final JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        mProgress.setVisibility(View.GONE);
        mList.setVisibility(View.VISIBLE);
    }
}
