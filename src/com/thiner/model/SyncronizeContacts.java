/*
 * 
 */

package com.thiner.model;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;

import com.thiner.utils.ThinerUtils;

import java.util.ArrayList;

/**
 * The Class SyncronizeContacts. to use this just instantiate an object of this
 * passing the list of persons the user has.
 */
public class SyncronizeContacts {

    /** The m person. */
    private final ArrayList<Person> mPerson;
    private final ContentResolver mContent;
    private final Context mContext;

    /**
     * Instantiates a new syncronize contacts.
     * 
     * @param person the person
     */
    public SyncronizeContacts(final ArrayList<Person> person, final ContentResolver content,
            final Context context) {
        mPerson = person;
        mContent = content;
        mContext = context;

    }

    /**
     * syncronize every contact the person has on thiner, with its alredy
     * created the class will delete the contact then will create a newer one
     * updated
     */
    public void syncronize() {
        if (mPerson == null) {
            ThinerUtils.showToast(mContext, "Ocorreu um erro ao passar a lista de pessoas");
        } else {
            for (final Person person : mPerson) {
                createContact(person);
            }
            ThinerUtils.showToast(mContext, "Contatos sincronizados com sua agenda");
        }

    }

    /**
     * Creates the contact.
     * 
     * @param person the person
     */
    private void createContact(final Person person) {
        final ContentResolver cr = mContent;

        final Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                final String existName = cur.getString(cur
                        .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                if (existName.contains(person.getFirstName() + " " +
                        person.getSecondName())) {
                    deleteContact(person);
                }
            }
        }

        final ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
        final int rawContactInsertIndex = ops.size();
        ops.add(ContentProviderOperation.newInsert(RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, "thiner")
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, "thiner comApp")
                .build());
        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                .withValue(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME,
                        person.getFirstName())
                .withValue(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME,
                        person.getSecondName())
                .build());

        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                .withValue(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Email.DATA,
                        person.getEmail())
                .withValue(ContactsContract.CommonDataKinds.Email.TYPE,
                        ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                .build());

        for (final Contact phone : person.getContacts()) {

            ops.add(ContentProviderOperation
                    .newInsert(Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID,
                            rawContactInsertIndex)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER,
                            phone.getDDD() + phone.getNumber() + "")
                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                            ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE
                    )
                    .build());
        }

        try {
            cr.applyBatch(ContactsContract.AUTHORITY, ops);
        } catch (final RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (final OperationApplicationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * Delete contact.
     * 
     * @param person the person
     */
    private void deleteContact(final Person person) {

        final ContentResolver cr = mContent;
        final String where = ContactsContract.Data.DISPLAY_NAME + " = ? ";
        final String[] params = new String[] {
                person.getFirstName() + person.getSecondName()
        };

        final ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
        ops.add(ContentProviderOperation.newDelete(ContactsContract.RawContacts.CONTENT_URI)
                .withSelection(where, params)
                .build());
        try {
            cr.applyBatch(ContactsContract.AUTHORITY, ops);
        } catch (final RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (final OperationApplicationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
