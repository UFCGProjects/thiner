
package com.thiner.model;

import com.google.common.base.Preconditions;

public class Contact implements Comparable<Contact> {

    private final String mOperadora;
    private final String mSecondName;
    private final String mFirstName;

    public Contact(final String firstName, final String secondName, final String operadora) {
        mFirstName = Preconditions.checkNotNull(firstName);
        mSecondName = Preconditions.checkNotNull(secondName);
        mOperadora = Preconditions.checkNotNull(operadora);
    }

    public String getOperadora() {
        return mOperadora;
    }

    public String getSecondName() {
        return mSecondName;
    }

    public String getFirstName() {
        return mFirstName;
    }

    @Override
    public int compareTo(final Contact arg0) {
        return getFirstName().compareTo(arg0.getFirstName());
    }
}
