/**
 * Copyright (C) 2014 Embedded Systems and Pervasive Computing Lab - UFCG All
 * rights reserved.
 */

package com.thiner.screen.main;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.thiner.R;
import com.thiner.asynctask.GetJSONTask;
import com.thiner.asynctask.GetJSONTask.GetJSONInterface;
import com.thiner.screen.contact.ContactActivity;
import com.thiner.screen.signup.SignUpActivity;
import com.thiner.utils.APIUtils;
import com.thiner.utils.ThinerUtils;

/**
 * The Class MainActivity.
 */
public final class MainActivity extends Activity implements GetJSONInterface {

    private EditText txtLogin;
    private EditText txtPassword;

    private Button btnSignIn;
    private Button btnSignUp;

    private int mLoginFailCount;

    private List<View> mViews;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Start Login Count
        mLoginFailCount = 0;

        // Get The Refference Of TextEdits
        txtLogin = (EditText) findViewById(R.id.editTextLogin);
        txtPassword = (EditText) findViewById(R.id.editTextPassword);

        // Get The Refference Of Buttons
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);

        mViews = new LinkedList<View>();
        mViews.add(txtLogin);
        mViews.add(txtPassword);
        mViews.add(btnSignIn);
        mViews.add(btnSignUp);

        btnSignIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                lockAll();
                signIn(v);
            }
        });

        // Set OnClick Listener on SignUp button
        btnSignUp.setOnClickListener(new View.OnClickListener() {
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
        final String username = txtLogin.getText().toString();
        final String password = txtPassword.getText().toString();

        if (Strings.isNullOrEmpty(username)) {
            ThinerUtils.showToast(this, "Missing username.");
        } else if (Strings.isNullOrEmpty(password)) {
            ThinerUtils.showToast(this, "Missing password.");
        } else {

            final String url = APIUtils.getApiUrlLogin() + "?"
                    + APIUtils.putAttrs("username", username) + "&"
                    + APIUtils.putAttrs("password", password);

            new GetJSONTask(this).execute(url);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();// Close The DatabaseloginDataBaseAdapter.close();
    }

    @Override
    public void callbackDownloadJSON(final JSONObject json) {
        final JSONObject users = Preconditions.checkNotNull(json);

        try {
            if (users.has("users") && users.getJSONArray("users").length() == 1) {
                startApp();
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

        txtLogin.setText("");
        txtPassword.setText("");

    }

    private void startApp() {
        final Intent intent = new Intent(MainActivity.this, ContactActivity.class);
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
