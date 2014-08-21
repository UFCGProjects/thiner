
package com.thiner.screen.person;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.thiner.R;
import com.thiner.adapter.PersonAdapter;
import com.thiner.asynctask.GetJSONTask;
import com.thiner.asynctask.GetJSONTask.GetJSONInterface;
import com.thiner.asynctask.PostJSONTask;
import com.thiner.asynctask.PostJSONTask.PostJSONInterface;
import com.thiner.model.Person;
import com.thiner.model.PhoneNumber;
import com.thiner.model.SyncronizeContacts;
import com.thiner.screen.profile.ProfileActivity;
import com.thiner.screen.request.RequestActivity;
import com.thiner.screen.search.SearchActivity;
import com.thiner.utils.APIUtils;
import com.thiner.utils.AuthPreferences;
import com.thiner.utils.DialogProfile;
import com.thiner.utils.MyLog;
import com.thiner.utils.ThinerUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The Class MainActivity.
 */
public final class PersonActivity extends Activity implements GetJSONInterface, PostJSONInterface {

    private ArrayList<Person> mListPersons;
    private PersonAdapter mPersonAdapter;
    private ListView mList;
    private ProgressBar mProgress;

    private static DialogProfile mDialogProfile;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        mListPersons = new ArrayList<Person>();

        mPersonAdapter = new PersonAdapter(this, R.layout.adapter_person, mListPersons);

        mList = (ListView) findViewById(android.R.id.list);

        mList.setAdapter(mPersonAdapter);
        mList.setEmptyView(findViewById(android.R.id.empty));

        mList.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(final AdapterView<?> parent,
                    final View view, final int position, final long id) {
                // Use the Builder class for convenient dialog construction
                final AlertDialog.Builder builder = new AlertDialog.Builder(
                        PersonActivity.this);

                builder.setTitle(R.string.remove_friend)
                        .setPositiveButton(R.string.remove,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(
                                            final DialogInterface dialog,
                                            final int id) {
                                        removeFriend(position);
                                    }
                                })
                        .setNegativeButton(R.string.cancel,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(
                                            final DialogInterface dialog,
                                            final int id) {
                                        // User cancelled the dialog
                                    }
                                });
                // Create the AlertDialog object and return it
                builder.create().show();

                return true;
            }

        });

        mProgress = (ProgressBar) findViewById(R.id.progressBar);

        mList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(final AdapterView<?> arg0, final View arg1, final int arg2,
                    final long arg3) {
                mDialogProfile = new DialogProfile(PersonActivity.this,
                        R.style.FullHeightDialog);
                mDialogProfile.show();

                mDialogProfile.setNameUser(mPersonAdapter.getItem(arg2).getUsername() + "\n");
                mDialogProfile.setTitle(mPersonAdapter.getItem(arg2).getFirstName() + " "
                        + mPersonAdapter.getItem(arg2).getSecondName());

                mDialogProfile.setUserEmail(mPersonAdapter.getEmailPerson() + "\n");

            }

        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        requestContacts();
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
                final Intent intentSearch = new Intent(PersonActivity.this,
                        SearchActivity.class);
                startActivity(intentSearch);
                return true;
            case R.id.profile:
                final Intent intentProfile = new Intent(PersonActivity.this,
                        ProfileActivity.class);
                startActivity(intentProfile);
                return true;
            case R.id.request:
                final Intent intentRequest = new Intent(PersonActivity.this,
                        RequestActivity.class);
                startActivity(intentRequest);
                return true;
            case R.id.syncronize:
                final SyncronizeContacts mSyncronize = new SyncronizeContacts(mListPersons,
                        getContentResolver(), getApplicationContext());
                mSyncronize.syncronize();
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

    public void updatePerson(final List<Person> array) {
        if (mListPersons != null && mPersonAdapter != null) {
            mListPersons.clear();

            mListPersons.addAll(array);

            Collections.sort(mListPersons);

            mPersonAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void callbackGetJSON(final JSONObject json) {
        MyLog.warning(json.toString());

        final List<Person> array = new ArrayList<Person>();

        try {
            final JSONArray friends = json.getJSONObject("users").getJSONArray("friends");

            for (int i = 0; i < friends.length(); i++) {
                //                MyLog.info(friends.getJSONObject(i).toString());

                final JSONObject friend = friends.getJSONObject(i);

                final String id = friend.getString("_id");
                final String firstName = friend.getString("firstname");
                final String secondName = friend.getString("lastname");
                final String username = friend.getString("username");
                final String email = friend.getString("lastname");
                final JSONArray contacts = friend.getJSONArray("contatos");
                final ArrayList<PhoneNumber> mcontacts = new ArrayList<PhoneNumber>();
                for (int j = 0; j < contacts.length(); j++) {
                    final String operadora = contacts.getJSONObject(j).getString("operadora");
                    final String ddd = contacts.getJSONObject(j).getString("DDD");
                    final String numero = contacts.getJSONObject(j).getString("numero")
                            .replace("-", "");

                    final PhoneNumber newPhoneNumber = new PhoneNumber(Integer.parseInt(ddd),
                            Integer.parseInt(numero), operadora);
                    mcontacts.add(newPhoneNumber);
                }

                final Person newPerson = new Person(id, firstName, secondName, username, email,
                        "Tim", mcontacts);
                array.add(newPerson);
            }

            updatePerson(array);

        } catch (final JSONException e) {
            e.printStackTrace();
        }

        mProgress.setVisibility(View.GONE);
        mList.setVisibility(View.VISIBLE);
    }

    public void removeFriend(final int position) {
        final Person person = mListPersons.get(position);

        final String params = APIUtils.putAttrs("id", AuthPreferences.getID(this))
                + "&" + APIUtils.putAttrs("friend", person.getID());

        new PostJSONTask(this).execute(APIUtils.getApiUrlRemoveFriend(), params);
    }

    public void requestContacts() {
        mList.setVisibility(View.GONE);
        mProgress.setVisibility(View.VISIBLE);

        final String url = APIUtils.getApiUrl() + "?"
                + APIUtils.putAttrs("id", AuthPreferences.getID(this));

        new GetJSONTask(this).execute(url);
    }

    @Override
    public void callbackPostJSON(final JSONObject json) {
        MyLog.info(json.toString());

        try {
            if (json.has("status") && json.getString("status").equals("success")) {
                ThinerUtils.showToast(this, json.getString("msg"));
                requestContacts();
            } else {
                ThinerUtils.showToast(this, json.getString("err"));
            }
        } catch (final JSONException e) {
            e.printStackTrace();
        }

    }
}
