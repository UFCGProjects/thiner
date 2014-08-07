
package com.thiner.screen.profile;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.thiner.R;
import com.thiner.asynctask.PostJSONTask;
import com.thiner.asynctask.PostJSONTask.PostJSONInterface;
import com.thiner.utils.APIUtils;
import com.thiner.utils.AuthPreferences;
import com.thiner.utils.ThinerUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

/**
 * The Class MainActivity.
 */
public final class ProfileActivity extends Activity implements PostJSONInterface {

    private EditText mEditTxtLastName;
    private EditText mEditTxtFirstName;

    private Button mBtnSaveChanges;
    private Button mBtnChangeEmail;
    private Button mBtnEditNumber;

    private List<View> mViews;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_screen);

        mEditTxtFirstName = (EditText) findViewById(R.id.editTextFirstName);
        mEditTxtLastName = (EditText) findViewById(R.id.editTextLastName);

        mBtnSaveChanges = (Button) findViewById(R.id.btnSaveNameChange);

        mBtnSaveChanges.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                lockAll();
                saveChanges();
            }
        });

        mBtnChangeEmail = (Button) findViewById(R.id.btnChangeEmail);
        mBtnEditNumber = (Button) findViewById(R.id.btnEditNumber);

        mViews = new LinkedList<View>();
        mViews.add(mEditTxtFirstName);
        mViews.add(mEditTxtLastName);
        mViews.add(mBtnSaveChanges);
        mViews.add(mBtnChangeEmail);
        mViews.add(mBtnEditNumber);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();// Close The DatabaseloginDataBaseAdapter.close();
    }

    protected void saveChanges() {
        final String firstname = mEditTxtFirstName.getText().toString();
        final String lastname = mEditTxtLastName.getText().toString();

        if (Strings.isNullOrEmpty(firstname)) {
            ThinerUtils.showToast(this, "You can't set a empty first name.");
        } else if (Strings.isNullOrEmpty(lastname)) {
            ThinerUtils.showToast(this, "You can't set a empty last name.");
        } else {

            final String params = APIUtils.putAttrs("id", AuthPreferences.getID(this))
                    + "&" + APIUtils.putAttrs("firstname", firstname)
                    + "&" + APIUtils.putAttrs("lastname", lastname);

            new PostJSONTask(this).execute(APIUtils.getApiUrlEditProfile(), params);
        }

        unlockAll();

    }

    @Override
    public void callbackPostJSON(final JSONObject json) {
        final JSONObject status = Preconditions.checkNotNull(json);

        try {
            if (status.has("status") && status.getString("status").equalsIgnoreCase("success")) {
                ThinerUtils.showToast(this, "Profile edited.");
            }
        } catch (final JSONException e) {
            e.printStackTrace();
        }
    }

    private void lockAll() {
        for (final View v : mViews) {
            v.setEnabled(false);
        }
    }

    private void unlockAll() {
        for (final View v : mViews) {
            v.setEnabled(true);
        }
    }
}
