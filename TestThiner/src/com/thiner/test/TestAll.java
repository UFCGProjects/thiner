
package com.thiner.test;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.EditText;


import com.robotium.solo.Solo;
import com.thiner.screen.main.MainActivity;
import com.thiner.screen.signup.SignUpActivity;
import com.thiner.R;

public class TestAll extends ActivityInstrumentationTestCase2<MainActivity> {

    private Solo mSolo;

    public TestAll() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mSolo = new Solo(getInstrumentation(), getActivity());
    }
    
    
    public void testeButtonSignUp() {
		View buttonSignUp = mSolo.getView(R.id.btnSignUp);		
		mSolo.waitForView(buttonSignUp);
		mSolo.clickOnView(buttonSignUp);
		View createAccount = mSolo.getView(R.id.buttonCreateAccount);				
		mSolo.enterText(0, "dirceudn");
		mSolo.enterText(1, "Dirceu");
		mSolo.enterText(2, "Medeiros");
		mSolo.enterText(3, "dirceudn@gmail.com");
		mSolo.enterText(4, "12345678");
		mSolo.enterText(5, "12345678");
		mSolo.waitForView(createAccount);
		mSolo.clickOnView(createAccount);
		
		mSolo.assertCurrentActivity("OK", SignUpActivity.class);
		mSolo.goBackToActivity("MainActivity");
		View buttonSign = mSolo.getView(R.id.btnSignIn);
		mSolo.sleep(200);		
		mSolo.enterText(0, "dirceudn");
		mSolo.enterText(1, "12345678");
		mSolo.searchEditText("carlos oliveira");
		mSolo.searchEditText("82828282");	
		mSolo.searchEditText("");
		mSolo.searchEditText("=");	
		mSolo.clickOnView(buttonSign);
		mSolo.assertCurrentActivity("OK",MainActivity.class );
		
		
	
	}

    public void testeButtonSigIn() {
		
		
		
		

	}

	



    @Override
    public void tearDown() throws Exception {
        // tearDown() is run after a test case has finished.
        // finishOpenedActivities() will finish all the activities that have
        // been opened during the test execution.
        mSolo.finishOpenedActivities();
    }
}
