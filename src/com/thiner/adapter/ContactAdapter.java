
package com.thiner.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.thiner.R;
import com.thiner.model.PhoneNumber;

import java.util.ArrayList;

public class ContactAdapter extends ArrayAdapter<PhoneNumber> {

    private final Context mContext;
    private final int mLayoutResourceId;
    private final ArrayList<PhoneNumber> mList;

    public ContactAdapter(final Context context, final int layoutResourceId,
            final ArrayList<PhoneNumber> mListPersons) {
        super(context, layoutResourceId, mListPersons);

        mContext = context;
        mLayoutResourceId = layoutResourceId;
        mList = mListPersons;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        View row = convertView;

        ContactHolder holder = null;

        if (row == null) {

            final LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(mLayoutResourceId, parent, false);

            holder = new ContactHolder();

            View view = row.findViewById(R.id.txtDDD);
            if (view instanceof TextView) {
                holder.txtDDD = (TextView) view;
            }

            view = row.findViewById(R.id.txtNumber);
            if (view instanceof TextView) {
                holder.txtNumber = (TextView) view;
            }

            view = row.findViewById(R.id.txtOperadora);
            if (view instanceof TextView) {
                holder.txtOperadora = (TextView) view;
            }

            row.setTag(holder);
        } else {
            holder = (ContactHolder) row.getTag();
        }

        final PhoneNumber phone = mList.get(position);

        holder.txtDDD.setText(phone.getDDD());
        holder.txtNumber.setText(phone.getNumber());
        holder.txtOperadora.setText(phone.getOperadora());

        return row;

    }

    /**
     * The Class PlayerHolder.
     */
    static class ContactHolder {

        TextView txtDDD;

        TextView txtNumber;

        TextView txtOperadora;

    }
}
