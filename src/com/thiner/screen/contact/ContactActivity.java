/**
 * Copyright (C) 2014 Embedded Systems and Pervasive Computing Lab - UFCG All
 * rights reserved.
 */

package com.thiner.screen.contact;

import android.app.Activity;
import android.os.Bundle;

import com.thiner.R;

/**
 * The Class MainActivity.
 */
public final class ContactActivity extends Activity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();// Close The DatabaseloginDataBaseAdapter.close();
    }

}
