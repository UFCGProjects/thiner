/**
 * Copyright (c) 2014 Sony Mobile Communications AB.
 * All rights, including trade secret rights, reserved.
 */

package com.thiner.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.thiner.R;

// TODO: Auto-generated Javadoc
/**
 * Dialog do perfil do usuário
 */
public class DialogProfile extends Dialog {

    /** The m cont. */
    private final Context mCont;

    /** The bt cancel. */
    private Button btCancel;

    /** The bt ok. */
    private Button btOK;

    /**
     * Constructor.
     * 
     * @param context of class
     * @param fullheightdialog
     */
    public DialogProfile(final Context context, final int fullheightdialog) {
        super(context);
        mCont = context;
    }

    /**
     * Create the activity.
     * 
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_profile);

        setCanceledOnTouchOutside(true);

    }

    /**
     * Change the name of user that is sending the file(s).
     * 
     * @param name of user
     */
    public final void setNameUser(final String name) {
        final TextView userNameSending = (TextView)
                findViewById(R.id.textNumber);
        userNameSending.setText("" + name + "");

    }

    public final void setUserEmail(final String email) {
        final TextView userNameNumber = (TextView) findViewById(R.id.textEmail);
        userNameNumber.setText("" + email + "");
    }

    /**
     * Change info in Dialog.
     * 
     * @param info to show in the Dialog
     */
    /*
     * public final void setInfo(final String info) { final TextView tvInfo =
     * (TextView) findViewById(R.id.info); tvInfo.setText(info); } public final
     * void setNumberContact(final String contactInfo) { final TextView tvInfo =
     * (TextView) findViewById(R.id.info); tvInfo.setText(info); }
     */

}
