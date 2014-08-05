
package com.thiner.screen.signup;

import org.json.JSONObject;

import com.thiner.R;
import com.thiner.asynctask.PostJSONTask;
import com.thiner.asynctask.PostJSONTask.PostJSONInterface;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUPActivity extends Activity implements PostJSONInterface {
    EditText edUsername;
    EditText edFirstName;
    EditText edLastName;
    EditText edEmail;
    EditText edPassword;
    EditText edConfirmPassword;
    Button btCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        // Get Refferences of Views

        // edConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);

        btCreateAccount = (Button) findViewById(R.id.buttonCreateAccount);
        btCreateAccount.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                sendPOSTUserInformation();

            }
        });
    }

    public void sendPOSTUserInformation() {
        edUsername = (EditText) findViewById(R.id.editTextUserName);
        edFirstName = (EditText) findViewById(R.id.etFirstName);
        edLastName = (EditText) findViewById(R.id.etLastName);
        edEmail = (EditText) findViewById(R.id.etEmail);
        edPassword = (EditText) findViewById(R.id.etPassword);

        String username = "username=" + edUsername.getText().toString();
        String password = "password=" + edPassword.getText().toString();
        String email = "email=" + edEmail.getText().toString();
        String firstname = "firstname=" + edFirstName.getText().toString();
        String lastname = "lastname=" + edLastName.getText().toString();

        new PostJSONTask(this).execute(username + "&" + password + "&"
                + email + "&" + firstname + "&" + lastname);

        Toast.makeText(getApplicationContext(),
                "Account Successfully Created ", Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

    }

    @Override
    public void callbackPostJSON(JSONObject msg) {
        // TODO Auto-generated method stub

    }
}
