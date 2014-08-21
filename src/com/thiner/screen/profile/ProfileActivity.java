
package com.thiner.screen.profile;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.common.base.CaseFormat;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.thiner.R;
import com.thiner.asynctask.PostJSONTask;
import com.thiner.asynctask.PostJSONTask.PostJSONInterface;
import com.thiner.model.PhoneNumber;
import com.thiner.utils.APIUtils;
import com.thiner.utils.AuthPreferences;
import com.thiner.utils.MyLog;
import com.thiner.utils.ThinerUtils;

import org.json.JSONArray;
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

    private List<View> mViews;
    private List<View> mImageButtons;

    private List<PhoneNumber> mContacts;

    private EditText mEditTxtConfirmNewPassword;
    private EditText mEditTxtNewPassword;
    private LinearLayout mLayoutPhone;
    private MenuItem mMenuEdit;
    private MenuItem mMenuSave;
    private View mEmptyPhoneBox;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mEditTxtFirstName = (EditText) findViewById(R.id.editTextFirstName);
        mEditTxtLastName = (EditText) findViewById(R.id.editTextLastName);
        mEditTxtNewPassword = (EditText) findViewById(R.id.editTextNewPassword);
        mEditTxtConfirmNewPassword = (EditText) findViewById(R.id.editTextConfirmNewPassword);
        mLayoutPhone = (LinearLayout) findViewById(R.id.phone_numbers);

        mBtnSaveChanges = (Button) findViewById(R.id.btnSaveNameChange);

        mBtnSaveChanges.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                saveChanges();
            }
        });

        mImageButtons = new LinkedList<View>();

        mViews = new LinkedList<View>();
        mViews.add(mEditTxtFirstName);
        mViews.add(mEditTxtLastName);
        mViews.add(mEditTxtNewPassword);
        mViews.add(mEditTxtConfirmNewPassword);
        mViews.add(mBtnSaveChanges);

        mEditTxtFirstName.setText(CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, AuthPreferences
                .getUserJSON(this).optString("firstname")));
        mEditTxtLastName.setText(CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, AuthPreferences
                .getUserJSON(this).optString("lastname")));

        final JSONObject userJSON = AuthPreferences.getUserJSON(this);
        final JSONArray phonesArrayJSON = userJSON.optJSONArray("contatos");

        if (phonesArrayJSON != null) {
            for (int i = 0; i < phonesArrayJSON.length(); i++) {

                try {
                    addPhoneBox(phonesArrayJSON.getJSONObject(i).optString("DDD"), phonesArrayJSON
                            .getJSONObject(i).optString("numero"), false);
                } catch (final JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        lockAll();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();// Close The DatabaseloginDataBaseAdapter.close();
    }

    /*
     * (non-Javadoc)
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);

        mMenuEdit = menu.findItem(R.id.edit);
        mMenuSave = menu.findItem(R.id.save);

        mMenuSave.setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.save:
                // @TODO: salvar e desabilitar edicao
                saveChanges();
                return true;
            case R.id.edit:
                enableEdit();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void enableEdit() {

        unlockAll();

        //        final MenuItem menuSave = mMenu.findItem(R.id.save);

        addPhoneBox("", "", true);

        mEmptyPhoneBox.findViewById(R.id.imageButtonAdd).setVisibility(View.VISIBLE);

        for (final View v : mImageButtons) {
            v.setVisibility(View.VISIBLE);
        }

        mMenuSave.setVisible(true);
        mMenuEdit.setVisible(false);

    }

    protected void saveChanges() {
        final String firstname = mEditTxtFirstName.getText().toString().trim();
        final String lastname = mEditTxtLastName.getText().toString().trim();
        final String newPass = mEditTxtNewPassword.getText().toString().trim();
        final String confirNewPass = mEditTxtConfirmNewPassword.getText().toString().trim();

        String params = APIUtils.putAttrs("id", AuthPreferences.getID(this));

        if (!Strings.isNullOrEmpty(firstname)) {
            params += "&" + APIUtils.putAttrs("firstname", firstname);
        }

        if (!Strings.isNullOrEmpty(lastname)) {
            params += "&" + APIUtils.putAttrs("lastname", lastname);

        }

        if (Strings.isNullOrEmpty(newPass) != Strings.isNullOrEmpty(confirNewPass)) {

            if (Strings.isNullOrEmpty(newPass)) {
                ThinerUtils.showToast(this, "You forget your new pass.");
                return;
            } else if (Strings.isNullOrEmpty(confirNewPass)) {
                ThinerUtils.showToast(this, "You forget to confirm your pass.");
                return;
            }

        } else if (!Strings.isNullOrEmpty(newPass) && !Strings.isNullOrEmpty(confirNewPass)) {

            if (newPass.equals(confirNewPass)) {
                params += "&" + APIUtils.putAttrs("password", newPass);
            } else {
                ThinerUtils.showToast(this, "Your confirm pass dont match.");
                return;
            }
        }

        if (params.length() == APIUtils.putAttrs("id", AuthPreferences.getID(this)).length()) {
            ThinerUtils.showToast(this, "You should change something.");
            return;
        }

        MyLog.debug("Making a post for edit profile...");
        new PostJSONTask(this).execute(APIUtils.getApiUrlEditProfile(), params);

        lockAll();

        mMenuSave.setVisible(false);
        mMenuEdit.setVisible(true);
    }

    @Override
    public void callbackPostJSON(final JSONObject json) {
        final JSONObject status = Preconditions.checkNotNull(json);

        MyLog.debug(json.toString());

        try {
            if (status.has("status") && status.getString("status").equalsIgnoreCase("success")) {
                ThinerUtils.showToast(this, "Profile edited.");
            } else {
                ThinerUtils.showToast(this, "Something wrong. :(");
            }
        } catch (final JSONException e) {
            e.printStackTrace();
        }

        //        mEditTxtFirstName.setText(CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, AuthPreferences
        //                .getUserJSON(this).optString("firstname")));
        //        mEditTxtLastName.setText(CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, AuthPreferences
        //                .getUserJSON(this).optString("lastname")));

        mEditTxtNewPassword.setText("");
        mEditTxtConfirmNewPassword.setText("");

        unlockAll();
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

    private void addPhoneBox(final String ddd, final String number, final boolean empty) {
        final View phoneView = getLayoutInflater().inflate(R.layout.adapter_number,
                null);
        final ImageButton ibAdd = (ImageButton) phoneView.findViewById(R.id.imageButtonAdd);
        final ImageButton ibDelete = (ImageButton) phoneView.findViewById(R.id.imageButtonDelete);
        final EditText etNumber = (EditText) phoneView.findViewById(R.id.editTextNumber);
        final EditText etDdd = (EditText) phoneView.findViewById(R.id.editTextDDD);
        final Spinner etOperadoras = (Spinner) phoneView.findViewById(R.id.spinnerOperadora);

        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.operadoras_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        etOperadoras.setAdapter(adapter);

        etNumber.setText(number);
        etDdd.setText(ddd);

        mLayoutPhone.addView(phoneView);

        if (empty) {
            mEmptyPhoneBox = phoneView;

        } else {

            //            mImageButtons.add(ibAdd);
            mImageButtons.add(ibDelete);

            mViews.add(etNumber);
            mViews.add(etDdd);
            mViews.add(etOperadoras);
        }

    }


    private void postContact()
}
