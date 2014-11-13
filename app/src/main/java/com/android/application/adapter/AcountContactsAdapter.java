package com.android.application.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.application.R;
import com.android.application.model.AccountContact;

import java.util.ArrayList;

/**
 * Created by noamm on 11/13/2014.
 */
public class AcountContactsAdapter extends BaseAdapter {

    private ArrayList<AccountContact> items;
    private LayoutInflater layoutInflater;

    public AcountContactsAdapter(Activity context) {
        layoutInflater = context.getLayoutInflater();
    }

    public void setList(ArrayList<AccountContact> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items == null ? 0 : items.size();
    }

    @Override
    public Object getItem(int position) {
        return items == null ? null : items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.row_acount_contact, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textView1 = (TextView) convertView.findViewById(R.id.textView1);
            viewHolder.textView2 = (TextView) convertView.findViewById(R.id.textView2);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        AccountContact accountContact = (AccountContact) getItem(position);
        if (accountContact != null) {
            viewHolder.textView1.setText(accountContact.name);
            viewHolder.textView2.setText(accountContact.phoneNumber);
        }

        return convertView;
    }

    static class ViewHolder {
        TextView textView1;
        TextView textView2;
    }
}
