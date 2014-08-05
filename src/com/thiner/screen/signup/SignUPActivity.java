package com.thiner.screen.signup;

import com.thiner.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUPActivity extends Activity
{
	EditText edUsername;
	EditText edFirstName;
	EditText edLastName;
	EditText edEmail;
	EditText edPassword;
	EditText edConfirmPassword;
	Button btCreateAccount;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup);
		
		
		
		// Get Refferences of Views
		edUsername=(EditText)findViewById(R.id.editTextUserName);
		/*edFirstName=(EditText)findViewById(R.id.editTextUserName);
		edLastName=(EditText)findViewById(R.id.editTextUserName);
		edEmail=(EditText)findViewById(R.id.editTextUserName);*/
	
		
		btCreateAccount=(Button)findViewById(R.id.buttonCreateAccount);
		btCreateAccount.setOnClickListener(new View.OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			/*
			String userName=editTextUserName.getText().toString();
			String password=editTextPassword.getText().toString();
			String confirmPassword=editTextConfirmPassword.getText().toString();*/
			
			// check if any of the fields are vaccant
		
		}
	});
}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		
	}
}
