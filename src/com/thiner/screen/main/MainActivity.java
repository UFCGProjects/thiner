
package com.thiner.screen.main;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.thiner.R;
import com.thiner.asynctask.GetJSONTask;
import com.thiner.asynctask.GetJSONTask.GetJSONInterface;
import com.thiner.screen.person.PersonActivity;
import com.thiner.screen.signup.SignUpActivity;
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
public final class MainActivity extends Activity implements GetJSONInterface {

    private EditText mTxtLogin;
    private EditText mTxtPassword;

    private Button mBtnSignIn;
    private Button mBtnSignUp;

    private int mLoginFailCount;

    private List<View> mViews;

    public static final String PREFS_NAME = "LoginPrefs";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        if (settings.getString("logged", "").toString().equals("logged")) {
            final Intent intent = new Intent(MainActivity.this,
                    PersonActivity.class);
            startActivity(intent);
        }

        // Start Login Count
        mLoginFailCount = 0;

        // Get The Refference Of TextEdits
        mTxtLogin = (EditText) findViewById(R.id.editTextLogin);
        mTxtPassword = (EditText) findViewById(R.id.editTextPassword);

        // Get The Refference Of Buttons
        mBtnSignIn = (Button) findViewById(R.id.btnSignIn);
        mBtnSignUp = (Button) findViewById(R.id.btnSignUp);

        mViews = new LinkedList<View>();
        mViews.add(mTxtLogin);
        mViews.add(mTxtPassword);
        mViews.add(mBtnSignIn);
        mViews.add(mBtnSignUp);

        mBtnSignIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                lockAll();
                signIn(v);
            }
        });

        // Set OnClick Listener on SignUp button
        mBtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // / Create Intent for SignUpActivity and Start The Activity
                final Intent intentSignUP = new Intent(getApplicationContext(),
                        SignUpActivity.class);
                startActivity(intentSignUP);
            }
        });
    }

    // Methos to handleClick Event of Sign In Button
    public void signIn(final View V) {
        final String username = mTxtLogin.getText().toString();
        final String password = mTxtPassword.getText().toString();

        if (Strings.isNullOrEmpty(username)) {
            ThinerUtils.showToast(this, "Missing username.");
        } else if (Strings.isNullOrEmpty(password)) {
            ThinerUtils.showToast(this, "Missing password.");
        } else {

            final String url = APIUtils.getApiUrlLogin() + "?"
                    + APIUtils.putAttrs("username", username) + "&"
                    + APIUtils.putAttrs("password", password);

            new GetJSONTask(this).execute(url);

            final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            final SharedPreferences.Editor editor = settings.edit();
            editor.putString("logged", "logged");
            editor.commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();// Close The DatabaseloginDataBaseAdapter.close();
    }

    @Override
    public void callbackGetJSON(final JSONObject json) {
        final JSONObject users = Preconditions.checkNotNull(json);

        try {
            if (users.has("users") && users.getJSONArray("users").length() == 1) {
                startApp(users.getJSONArray("users").getJSONObject(0));
            } else {
                loginFail();
            }
        } catch (final JSONException e) {
            e.printStackTrace();
        }

        unlockAll();
    }

    private void loginFail() {
        mLoginFailCount++;

        if (mLoginFailCount > 3) {
            ThinerUtils.showToast(this, "Do you have account here?");
        } else {
            ThinerUtils.showToast(this, "Username or Password incorrect.");
        }

        mTxtLogin.setText("");
        mTxtPassword.setText("");

    }

    private void startApp(final JSONObject jsonObject) {

        try {
            AuthPreferences.setId(this, jsonObject.getString("_id"));
            AuthPreferences.setUserJSON(jsonObject);

        } catch (final JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        final Intent intent = new Intent(MainActivity.this,
                PersonActivity.class);
        startActivity(intent);
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
