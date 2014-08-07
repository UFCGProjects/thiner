
package com.thiner.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.thiner.R;
import com.thiner.model.Contact;

import java.util.ArrayList;

public class ContactAdapter extends ArrayAdapter<Contact> {

    private final Context mContext;
    private final int mLayoutResourceId;
    private final ArrayList<Contact> mList;

    public ContactAdapter(final Context context, final int layoutResourceId,
            final ArrayList<Contact> list) {
        super(context, layoutResourceId, list);

        mContext = context;
        mLayoutResourceId = layoutResourceId;
        mList = list;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        View row = convertView;

        ContactHolder holder = null;

        if (row == null) {

            final LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(mLayoutResourceId, parent, false);

            holder = new ContactHolder();

            View view = row.findViewById(R.id.textViewFirstName);
            if (view instanceof TextView) {
                holder.txtFirstName = (TextView) view;
            }

            view = row.findViewById(R.id.textViewSecondName);
            if (view instanceof TextView) {
                holder.txtSecondName = (TextView) view;
            }

            view = row.findViewById(R.id.textViewOperadora);
            if (view instanceof TextView) {
                holder.txtOperadora = (TextView) view;
            }

            row.setTag(holder);
        } else {
            holder = (ContactHolder) row.getTag();
        }

        final Contact contact = mList.get(position);

        holder.txtFirstName.setText(contact.getFirstName());
        holder.txtSecondName.setText(contact.getSecondName());
        holder.txtOperadora.setText(contact.getOperadora());

        return row;

    }

    /**
     * The Class PlayerHolder.
     */
    static class ContactHolder {

        TextView txtFirstName;

        TextView txtSecondName;

        TextView txtOperadora;

    }
}
