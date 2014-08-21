package com.thiner.test;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;

import com.robotium.solo.Solo;
import com.thiner.screen.main.MainActivity;
import com.thiner.screen.person.PersonActivity;
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
		View buttonSign = mSolo.getView(R.id.btnSignIn); //
		mSolo.enterText(0, "dirceudn");
		mSolo.enterText(1, "12345678");
		mSolo.searchEditText("carlos oliveira");
		mSolo.searchEditText("82828282");
		mSolo.searchEditText("");
		mSolo.searchEditText("=");
		mSolo.clickOnView(buttonSign);
		mSolo.clearEditText(0);
		mSolo.clearEditText(1);
		mSolo.enterText(0, "dirceudn");
		mSolo.enterText(1, "aa");
		mSolo.clickOnView(buttonSign);
		mSolo.assertCurrentActivity("OK", PersonActivity.class);
		View actionButton = mSolo.getView(R.id.search);
		mSolo.sleep(500);
		mSolo.clickOnView(actionButton);
		mSolo.sleep(500);
		mSolo.searchEditText("dirceudn");
		mSolo.sleep(500);
		mSolo.goBackToActivity("PersonActivity");
		View editProfile = mSolo.getView(R.id.profile);
		mSolo.sleep(500);
		mSolo.clickOnView(editProfile);
		mSolo.sleep(500);
		mSolo.goBackToActivity("PersonActivity");

		mSolo.clickInList(1);
		mSolo.goBack();
		mSolo.clickInList(2);
		mSolo.goBack();
		mSolo.clickInList(3);
		mSolo.goBack();
		mSolo.clickLongInList(2);
		mSolo.goBack();

		View syncronize = mSolo.getView(R.id.syncronize);
		mSolo.sleep(500);
		mSolo.clickOnView(syncronize);
		mSolo.sleep(500);
		View request = mSolo.getView(R.id.request);
		mSolo.sleep(500);
		mSolo.clickOnView(request);
		mSolo.sleep(500);
		mSolo.goBackToActivity("PersonActivity");
		View editProfile2 = mSolo.getView(R.id.profile);
		mSolo.sleep(500);
		mSolo.clickOnView(editProfile2);
		mSolo.sleep(500);
		View edit = mSolo.getView(R.id.edit);
		mSolo.sleep(500);
		mSolo.clickOnView(edit);
		mSolo.sleep(500);
		mSolo.clearEditText(0);
		mSolo.clearEditText(1);
		mSolo.enterText(0, "dirceu");
		mSolo.enterText(1, "teixeira");
		View save = mSolo.getView(R.id.save);
		mSolo.sleep(500);
		mSolo.clickOnView(save);
		mSolo.sleep(500);

	}

	@Override
	public void tearDown() throws Exception {
		// tearDown() is run after a test case has finished.
		// finishOpenedActivities() will finish all the activities that have
		// been opened during the test execution.
		mSolo.finishOpenedActivities();
	}
}
