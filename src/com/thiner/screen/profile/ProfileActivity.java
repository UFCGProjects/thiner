
package com.thiner.screen.profile;

import android.app.Activity;
import android.os.Bundle;

import com.thiner.R;

/**
 * The Class MainActivity.
 */
public final class ProfileActivity extends Activity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_screen);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();// Close The DatabaseloginDataBaseAdapter.close();
    }

}
