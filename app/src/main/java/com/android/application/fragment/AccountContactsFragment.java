package com.android.application.fragment;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.application.R;
import com.android.application.adapter.AcountContactsAdapter;
import com.android.application.model.AccountContact;

import java.util.ArrayList;

/**
 * Created by noamm on 11/13/2014.
 */
public class AccountContactsFragment extends Fragment {

    private ListView listView;
    private ArrayList<AccountContact> accountContacts;
    private AcountContactsAdapter acountContactsAdapter;

    public AccountContactsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        listView = (ListView) rootView.findViewById(R.id.listView);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        acountContactsAdapter = new AcountContactsAdapter(getActivity());
        listView.setAdapter(acountContactsAdapter);
        accountContacts = getPhoneContact();
        acountContactsAdapter.setList(accountContacts);
    }

    private ArrayList<AccountContact> getPhoneContact() {
        ArrayList<AccountContact> contacts = new ArrayList<AccountContact>();

        Cursor phones = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        while (phones.moveToNext()) {
            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            AccountContact accountContact = new AccountContact();
            accountContact.name = name;
            accountContact.phoneNumber = phoneNumber;
            accountContact.isSelected = false;
            contacts.add(accountContact);
        }
        phones.close();
        return contacts;
    }


}